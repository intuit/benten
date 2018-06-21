package com.intuit.benten.jira.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.jira.model.ghc.Sprint;
import com.intuit.benten.jira.model.ghc.SprintReport;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class JiraGhClient extends BentenHttpClient{

    private static final Logger logger = LoggerFactory.getLogger(BentenJiraClient.class);

    ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public List<Sprint> sprints(String boardId)  {
       try {
           
           HttpGet httpGet = new HttpGet(JiraGhHttpHelper.sprintsUri(boardId));
           HttpResponse httpResponse = request(httpGet);
           if (httpResponse.getStatusLine().getStatusCode() != 200) {
               throw new BentenJiraException(httpResponse.getStatusLine().getReasonPhrase());
           }
           String json = EntityUtils
                   .toString(httpResponse.getEntity());
           JSONObject jsonObject=
                   objectMapper.readValue(json, JSONObject.class);
           if(!JiraConverter.isNull(jsonObject.get("sprints"))){
               List<Sprint> sprints =
                       objectMapper.readValue(jsonObject.get("sprints").toString(),new TypeReference<List<Sprint>>(){});
               if(!sprints.isEmpty())
                   return sprints;
           }

           return null;
       }catch(Exception e ){
           logger.error(e.getMessage(),e);
           throw new BentenJiraException(e.getMessage());
       }
    }

    public SprintReport sprintReport(String boardId,String sprintId)  {
        try {
            HttpGet httpGet = new HttpGet(JiraGhHttpHelper.sprintReportUri(boardId,sprintId));
            HttpResponse httpResponse = request(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new BentenJiraException(httpResponse.getStatusLine().getReasonPhrase());
            }
            String json = EntityUtils
                    .toString(httpResponse.getEntity());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
            dateFormat.set2DigitYearStart(new GregorianCalendar(2001,1,1)
                    .getTime());
            objectMapper.setDateFormat(dateFormat);
            SprintReport sprintReport = objectMapper
                        .readValue(json,SprintReport.class);

            return sprintReport;
        }catch(Exception e ){
            logger.error(e.getMessage(),e);
            throw new BentenJiraException(e.getMessage());
        }
    }

}
