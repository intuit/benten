package com.intuit.benten.jira.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.jira.model.JiraError;
import com.intuit.benten.jira.model.Project;
import com.intuit.benten.jira.model.Transition;
import com.intuit.benten.jira.model.Issue;
import net.sf.json.JSONObject;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class JiraHttpClient extends BentenHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(JiraHttpClient.class);

    public List<Issue> searchIssues(String jql,String expandedFields,int maxResults){
        List<Issue> issues;
        try {
            HttpGet httpGet = new HttpGet(JiraHttpHelper.createSearchUri(jql, null, expandedFields, maxResults, 0));
            HttpResponse httpResponse = request(httpGet);

            if(httpResponse.getStatusLine().getStatusCode()!=200){

               handleJiraException(httpResponse);
            }

            String json = EntityUtils.toString(httpResponse.getEntity());

            JSONObject issuesJson = JiraConverter.objectMapper.readValue(json,JSONObject.class);
            issues = JiraConverter.convertIssuesMapToIssues(issuesJson);
        }catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            throw new RuntimeException(ex);
        }
        return issues;
    }


    public Issue getIssueDetails(String issueKey,String expandedFields){
        Issue issue;
        try {
            HttpGet httpGet = new HttpGet(JiraHttpHelper.issueDetailsUri(issueKey, expandedFields));
            HttpResponse httpResponse = request(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()!=200){
                handleJiraException(httpResponse);
            }
            String json = EntityUtils.toString(httpResponse.getEntity());

            JSONObject issuesJson = JiraConverter.objectMapper.readValue(json,JSONObject.class);
            issue =JiraConverter.convertJsonObjectToIssue(issuesJson);
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return issue;
    }

    public void updateIssue(String issueKey,Map<String,Object> fields){

        try {
            HttpPut httpPut = new HttpPut(JiraHttpHelper.issueUri(issueKey));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fields",fields);
            StringEntity stringEntity = formPayload(jsonObject);
            stringEntity.setContentType("application/json");
            httpPut.setEntity(stringEntity);
            HttpResponse httpResponse = request(httpPut);
            if(httpResponse.getStatusLine().getStatusCode()!=204){
                handleJiraException(httpResponse);
            }

        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void logWork(String issueKey,JSONObject jsonObject){

        try {
            HttpPost httpPost = new HttpPost(JiraHttpHelper.logWorkUri(issueKey));
            StringEntity stringEntity = formPayload(jsonObject);
            httpPost.setEntity(stringEntity);
            HttpResponse httpResponse = request(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()!=201){
                handleJiraException(httpResponse);
            }

        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void transitionIssue(String issueKey,JSONObject jsonObject){

        try {
            HttpPost httpPost = new HttpPost(JiraHttpHelper.possibleTransitionsUri(issueKey));
            StringEntity stringEntity = formPayload(jsonObject);
            httpPost.setEntity(stringEntity);
            HttpResponse httpResponse = request(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()!=204){
                handleJiraException(httpResponse);
            }

        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Transition> getPossibleTransitions(String issueKey){

        try {
            HttpGet httpGet = new HttpGet(JiraHttpHelper.possibleTransitionsUri(issueKey));
            HttpResponse httpResponse = request(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()!=200){
               handleJiraException(httpResponse);
            }
            String json = EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject = JiraConverter.objectMapper
                    .readValue(json,JSONObject.class);
            List<Transition> transitions= JiraConverter.objectMapper
                            .readValue(jsonObject.get("transitions").toString(),new TypeReference<List<Transition>>(){});
            return transitions;
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void comment(String issueKey,JSONObject jsonObject){

        try {
            HttpPost httpPost = new HttpPost(JiraHttpHelper.commentUri(issueKey));
            StringEntity stringEntity = formPayload(jsonObject);
            httpPost.setEntity(stringEntity);
            HttpResponse httpResponse = request(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()!=201){
                handleJiraException(httpResponse);
            }

        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public JSONObject getCreateMetaData(String projectKey, String issueType){
        try{
            Map<String, String> params = new HashMap();
            params.put("expand", "projects.issuetypes.fields");
            params.put("projectKeys", projectKey);
            params.put("issuetypeNames", issueType);
            HttpGet httpGet = new HttpGet(JiraHttpHelper.metaDataUri(params));
            HttpResponse httpResponse = request(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()!=200){
                handleJiraException(httpResponse);
            }
            String json = EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject = JiraConverter.objectMapper
                    .readValue(json,JSONObject.class);
            List<Project> projects = JiraConverter.objectMapper.readValue(jsonObject.get("projects").toString(),new TypeReference<List<Project>>(){});

            if(projects.isEmpty() || projects.get(0).getIssuetypes().isEmpty()) {
                throw new BentenJiraException("Project '"+ projectKey + "'  or issue type '" + issueType +
                        "' missing from create metadata. Do you have enough permissions?");
            }
            return projects.get(0).getIssuetypes().get(0).getFields();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public String createIssue(Map<String,Object> fields){
        try{

            HttpPost httpPost = new HttpPost(JiraHttpHelper.createIssueUri());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fields",fields);
            StringEntity stringEntity = formPayload(jsonObject);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            HttpResponse httpResponse = request(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()!=201){
                handleJiraException(httpResponse);
            }
            String json = EntityUtils.toString(httpResponse.getEntity());
            JSONObject response = JiraConverter.objectMapper
                    .readValue(json,JSONObject.class);
           return JiraHttpHelper.browseIssueUri((String)response.get("key")).toString();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private StringEntity formPayload(JSONObject jsonObject) throws JsonProcessingException {
        String payload;
        StringEntity stringEntity;
        payload =  JiraConverter.objectMapper.writeValueAsString(jsonObject);
        stringEntity = new StringEntity(payload, "UTF-8");
        stringEntity.setContentType("application/json");
        return stringEntity;
    }


    private void handleJiraException(HttpResponse httpResponse) throws IOException {
        String json = EntityUtils.toString(httpResponse.getEntity());
        JiraError jiraError = JiraConverter.objectMapper.readValue(json, JiraError.class);
        JSONObject error = jiraError.getErrors();
        if(error.size()>0) {
            String firstKey = (String) error.keys().next();
            throw new BentenJiraException( error.getString(firstKey));
        }else{
            throw new BentenJiraException( jiraError.getErrorMessages().get(0));
        }

    }


}
