package com.intuit.benten.jira.http;

import com.intuit.benten.jira.properties.JiraProperties;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class JiraAgileHttpHelper {

    public static String getBaseUri() {
        return JiraProperties.baseurl+"/rest/agile/1.0/";
    }

    public static URI sprintIssuesUri(String sprintId) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap();
        queryParams.put("expand","changelog");
        queryParams.put("jql","(type=story OR type=bug)");
        URI searchUri = JiraHttpHelper.buildURI(getBaseUri() + "sprint/"+sprintId+"/issue", queryParams);
        return searchUri;
    }

    public static URI boardIdByNameUri(String name) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap();
        queryParams.put("name",name);
        URI searchUri = JiraHttpHelper.buildURI(getBaseUri() + "board", queryParams);
        return searchUri;
    }
}
