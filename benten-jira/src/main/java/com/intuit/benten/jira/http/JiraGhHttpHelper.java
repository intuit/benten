package com.intuit.benten.jira.http;

import com.intuit.benten.jira.properties.JiraProperties;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class JiraGhHttpHelper {


    public static String getBaseUri() {
        return JiraProperties.baseurl+"/rest/greenhopper/1.0/";
    }

    public static URI sprintsUri(String boardId) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap();
        URI searchUri = JiraHttpHelper.buildURI(getBaseUri() + "sprintquery/"+boardId, queryParams);
        return searchUri;
    }

    public static URI sprintReportUri(String boardId,String sprintId) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap();
        queryParams.put("expand","changelog");
        queryParams.put("rapidViewId",boardId);
        queryParams.put("sprintId",sprintId);
        URI searchUri = JiraHttpHelper.buildURI(getBaseUri() + "rapid/charts/sprintreport", queryParams);
        return searchUri;
    }

}
