package com.intuit.benten.jira.converters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.jira.model.Field;
import com.intuit.benten.jira.model.Issue;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class JiraConverter {

    public static ObjectMapper objectMapper= new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));

    public static final class Meta {
        public boolean required;
        public String type;
        public String items;
        public String name;
        public String system;
        public String custom;
        public int customId;
    }

    public static final class ValueTuple {
        public final String type;
        public final Object value;
        public ValueTuple(String type, Object value) {
            this.type = type;
            this.value = (value != null ? value : JSONNull.getInstance());
        }
        public ValueTuple(ValueType type, Object value) {
            this(type.toString(), value);
        }
    }

    public enum ValueType {
        KEY("key"), NAME("name"), ID_NUMBER("id"), VALUE("value");
        private String typeName;

        private ValueType(String typeName) {
            this.typeName = typeName;
        }

        @Override
        public String toString() {
            return typeName;
        }
    };

    public static final class Operation {
        public String name;
        public Object value;

        public Operation(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }


    public final static String CommonExpandedFields = Field.ASSIGNEE
            .concat(","+ Field.STATUS)
            .concat(","+ Field.PRIORITY)
            .concat(","+ Field.ISSUE_TYPE)
            .concat(","+ Field.PRIORITY)
            .concat(","+ Field.SUMMARY)
            .concat(","+ Field.DESCRIPTION)
            .concat(","+ Field.COMMENT);

    public static List<Issue> convertIssuesMapToIssues(JSONObject issuesMap) throws IOException {

        List<JSONObject> jiraIssues =  objectMapper.readValue(issuesMap.getString("issues"),
                new TypeReference<List<JSONObject>>(){});
        List<Issue> issues = new ArrayList<Issue>();
        for (JSONObject jiraIssue: jiraIssues) {
            Issue issue = new Issue(jiraIssue);
            issues.add(issue);
        }
        return issues;
    }

    public static Issue convertJsonObjectToIssue(JSONObject jiraIssue) throws IOException {
            Issue issue = new Issue(jiraIssue);
            return issue;
    }

    public static Date getDateTime(String date) {
        Date result = null;

        SimpleDateFormat df = new SimpleDateFormat(Field.DATETIME_FORMAT);
        result = df.parse((String)date, new ParsePosition(0));

        return result;
    }

    public static boolean isNull(Object object){
        if(object.equals(null))
            return true;
        else
            return false;
    }
    public static Object toJson(String name, Object value, JSONObject editmeta)
            throws BentenJiraException, UnsupportedOperationException {

        Meta m = getFieldMetadata(name, editmeta);
        if (m.type == null)
            throw new BentenJiraException("Field '" + name + "' is missing metadata type");

        if (m.type.equals("array")) {
            if (value == null)
                value = new ArrayList();
            else if (!(value instanceof Iterable))
                throw new BentenJiraException("Field '" + name + "' expects an Iterable value");

            return toArray((Iterable)value, m.items, m.custom);
        } else if (m.type.equals("date")) {
            if (value == null)
                return JSONNull.getInstance();

            Date d = toDate(value);
            if (d == null)
                throw new BentenJiraException("Field '" + name + "' expects a date value or format is invalid");

            SimpleDateFormat df = new SimpleDateFormat(Field.DATE_FORMAT);
            return df.format(d);
        } else if (m.type.equals("datetime")) {
            if (value == null)
                return JSONNull.getInstance();
            else if (!(value instanceof Timestamp))
                throw new BentenJiraException("Field '" + name + "' expects a Timestamp value");

            SimpleDateFormat df = new SimpleDateFormat(Field.DATETIME_FORMAT);
            return df.format(value);
        } else if (m.type.equals("issuetype") || m.type.equals("priority") ||
                m.type.equals("user") || m.type.equals("resolution") || m.type.equals("securitylevel")) {
            JSONObject json = new JSONObject();

            if (value == null)
                return JSONNull.getInstance();
            else if (value instanceof ValueTuple) {
                ValueTuple tuple = (ValueTuple)value;
                json.put(tuple.type, tuple.value.toString());
            } else
                json.put(ValueType.NAME.toString(), value.toString());

            return json.toString();
        } else if (m.type.equals("project") || m.type.equals("issuelink")) {
            JSONObject json = new JSONObject();

            if (value == null)
                return JSONNull.getInstance();
            else if (value instanceof ValueTuple) {
                ValueTuple tuple = (ValueTuple)value;
                json.put(tuple.type, tuple.value.toString());
            } else
                json.put(ValueType.KEY.toString(), value.toString());

            return json.toString();
        } else if (m.type.equals("string") || (m.type.equals("securitylevel") || m.type.equals("option") || m.type.equals("option-with-child"))) {
            if (value == null)
                return "";
            else if (value instanceof List)
                return toJsonMap((List)value);
            else if (value instanceof ValueTuple) {
                JSONObject json = new JSONObject();
                ValueTuple tuple = (ValueTuple)value;
                json.put(tuple.type, tuple.value.toString());
                return json.toString();
            }

            return value.toString();
        }  else if (m.type.equals("number")) {
            if (value == null) //Non mandatory number fields can be set to null
                return JSONNull.getInstance();
            else if(!(value instanceof java.lang.Integer) && !(value instanceof java.lang.Double) && !(value
                    instanceof java.lang.Float) && !(value instanceof java.lang.Long) )
            {
                throw new BentenJiraException("Field '" + name + "' expects a Numeric value");
            }
            return value;
        } else if (m.type.equals("any")) {
            if (value == null)
                return JSONNull.getInstance();
            else if (value instanceof List)
                return toJsonMap((List)value);
            else if (value instanceof ValueTuple) {
                JSONObject json = new JSONObject();
                ValueTuple tuple = (ValueTuple)value;
                json.put(tuple.type, tuple.value.toString());
                return json.toString();
            }

            return value;
        }

        throw new UnsupportedOperationException(m.type + " is not a supported field type");
    }

    public static Object toJsonMap(List list) {
        JSONObject json = new JSONObject();

        for (Object item : list) {
            if (item instanceof ValueTuple) {
                ValueTuple vt = (ValueTuple)item;
                json.put(vt.type, vt.value.toString());
            } else
                json.put(ValueType.VALUE.toString(), item.toString());
        }

        return json.toString();
    }


    public static Meta getFieldMetadata(String name, JSONObject editmeta)
            throws BentenJiraException {

        if (editmeta.isNullObject() || !editmeta.containsKey(name))
            throw new BentenJiraException("Field '" + name + "' does not exist or read-only");

        Map f = (Map)editmeta.get(name);
        Meta m = new Meta();

        m.required = (boolean)f.get("required");
        m.name = (String)f.get("name");

        if (!f.containsKey("schema"))
            throw new BentenJiraException("Field '" + name + "' is missing schema metadata");

        Map schema = (Map)f.get("schema");

        m.type = getString(schema.get("type"));
        m.items = getString(schema.get("items"));
        m.system = getString(schema.get("system"));
        m.custom = getString(schema.get("custom"));
        m.customId = getInteger(schema.get("customId"));

        return m;
    }

    public static JSONArray toArray(Iterable iter, String type, String custom) throws BentenJiraException {
        JSONArray results = new JSONArray();

        if (type == null)
            throw new BentenJiraException("Array field metadata is missing item type");

        for (Object val : iter) {
            Operation oper = null;
            Object realValue = null;
            Object realResult = null;

            if (val instanceof Operation) {
                oper = (Operation)val;
                realValue = oper.value;
            } else
                realValue = val;

            if (type.equals("component") || type.equals("group") ||
                    type.equals("user") || type.equals("version")) {

                JSONObject itemMap = new JSONObject();

                if (realValue instanceof ValueTuple) {
                    ValueTuple tuple = (ValueTuple)realValue;
                    itemMap.put(tuple.type, tuple.value.toString());
                } else
                    itemMap.put(ValueType.NAME.toString(), realValue.toString());

                realResult = itemMap;
            } else if ( type.equals("option") ||
                    (
                            type.equals("string") && custom != null
                                    && (custom.equals("com.atlassian.jira.plugin.system.customfieldtypes:multicheckboxes") ||
                                    custom.equals("com.atlassian.jira.plugin.system.customfieldtypes:multiselect")))) {

                realResult = new JSONObject();
                ((JSONObject)realResult).put(ValueType.VALUE.toString(), realValue.toString());
            } else if (type.equals("string"))
                realResult = realValue.toString();

            if (oper != null) {
                JSONObject operMap = new JSONObject();
                operMap.put(oper.name, realResult);
                results.add(operMap);
            } else
                results.add(realResult);
        }

        return results;
    }

    public static String getString(Object s) {
        String result = null;

        if (s instanceof String)
            result = (String)s;

        return result;
    }

    public static int getInteger(Object i) {
        int result = 0;

        if (i instanceof Integer)
            result = ((Integer)i).intValue();

        return result;
    }

    public static Date toDate(Object value) {
        if (value instanceof Date || value == null)
            return (Date)value;

        String dateStr = value.toString();
        SimpleDateFormat df = new SimpleDateFormat(Field.DATE_FORMAT);
        if (dateStr.length() > Field.DATE_FORMAT.length()) {
            df = new SimpleDateFormat(Field.DATETIME_FORMAT);
        }
        return df.parse(dateStr, new ParsePosition(0));
    }

    public static List<JSONObject> getRequiredFields(JSONObject fields){
        List<JSONObject> requiredFields = new ArrayList<>();

        Iterator<?> iterator = fields.keys();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            JSONObject jsonChildObject = fields.getJSONObject(key);

            boolean required = jsonChildObject.getBoolean("required");
            if(required) {
                if(!key.startsWith("customfield")) {
                    if ("Project".equals(jsonChildObject.getString("name")) || "Issue Type".equals(jsonChildObject.getString("name"))
                            || "Summary".equals(jsonChildObject.getString("name")))
                        continue;
                }
                jsonChildObject.put("key", key);
                requiredFields.add(jsonChildObject);

            }

        }
        return requiredFields;
    }
}
