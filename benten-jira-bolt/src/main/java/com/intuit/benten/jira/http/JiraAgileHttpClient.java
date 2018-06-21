package com.intuit.benten.jira.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.jira.model.agile.Board;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class JiraAgileHttpClient extends BentenHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(JiraAgileHttpClient.class);

    public List<Board> boardsByName(String boardName)  {
        try {
            HttpGet httpGet = new HttpGet(JiraAgileHttpHelper.boardIdByNameUri(boardName));
            HttpResponse httpResponse = request(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new BentenJiraException(httpResponse.getStatusLine().getReasonPhrase());
            }
            String json = EntityUtils
                    .toString(httpResponse.getEntity());
            JSONObject jsonObject = JiraConverter.objectMapper.readValue(json, JSONObject.class);

            if(!JiraConverter.isNull(jsonObject.get("values") )){
               List<Board> boardNames = JiraConverter.objectMapper.readValue(jsonObject.get("values").toString(),new TypeReference<List<Board>>(){});
               if(!boardNames.isEmpty())
                   return boardNames;
            }

            return null;
        }catch(Exception e ){
            logger.error(e.getMessage(),e);
            throw new BentenJiraException(e.getMessage());
        }
    }

    public List<Issue> sprintIssues(String sprintId)  {
        try {
            HttpGet httpGet = new HttpGet(JiraAgileHttpHelper.sprintIssuesUri(sprintId));
            HttpResponse httpResponse = request(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new BentenJiraException(httpResponse.getStatusLine().getReasonPhrase());
            }
            String json = EntityUtils
                    .toString(httpResponse.getEntity());
            JSONObject jsonObject =JiraConverter.objectMapper.readValue(json, JSONObject.class);

            List<Issue> issues =JiraConverter.convertIssuesMapToIssues(jsonObject);

            return issues;
        }catch(Exception e ){
            logger.error(e.getMessage(),e);
            throw new BentenJiraException(e.getMessage());
        }
    }

}
