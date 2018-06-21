package com.intuit.benten.jira.http;

import com.intuit.benten.jira.properties.JiraProperties;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class JiraHttpHelper{

    public static String getBaseUri() {
        return JiraProperties.baseurl+"/rest/api/2/";
    }

    public static URI buildURI(String path, Map<String, String> params) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPath(path);
        if (params != null) {
            Iterator var4 = params.entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<String, String> ent = (Map.Entry)var4.next();
                uriBuilder.addParameter((String)ent.getKey(), (String)ent.getValue());
            }
        }

        return uriBuilder.build();
    }

    public static URI issueUri(String issueKey) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap();
        URI searchUri = buildURI(getBaseUri() + "issue/"+issueKey, queryParams);
        return searchUri;
    }

    public static URI logWorkUri(String issueKey) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap();
        URI searchUri = buildURI(getBaseUri() + "issue/"+issueKey+"/worklog", queryParams);
        return searchUri;
    }

    public static URI possibleTransitionsUri(String issueKey) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap();
        URI searchUri = buildURI(getBaseUri() + "issue/"+issueKey+"/transitions", queryParams);
        return searchUri;
    }

    public static URI commentUri(String issueKey) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap();
        URI searchUri = buildURI(getBaseUri() + "issue/"+issueKey+"/comment", queryParams);
        return searchUri;
    }

    public static URI metaDataUri(Map queryParams) throws URISyntaxException {
        URI metaDataUri = buildURI(getBaseUri() + "issue/createmeta", queryParams);
        return metaDataUri;
    }


    public static URI browseIssueUri(String issueKey) throws URISyntaxException {
        URI browseIssueuri;
        if(JiraProperties.herokuurl!=null && !JiraProperties.herokuurl.isEmpty()){
            browseIssueuri = buildURI(JiraProperties.herokuurl +"/browse/"+issueKey,null);
        }else {
            browseIssueuri = buildURI(JiraProperties.baseurl + "/browse/" + issueKey, null);
        }
        return browseIssueuri;
    }

    public static URI createIssueUri() throws URISyntaxException {
        URI createIssueuri = buildURI(getBaseUri()+"issue",null);
        return createIssueuri;
    }

    public static URI issueDetailsUri(String issueKey, String expandFields) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap();

        if (expandFields != null) {
            queryParams.put("expand", expandFields);
        }

        URI searchUri = buildURI(getBaseUri() + "issue/"+issueKey, queryParams);
        return searchUri;
    }

    public static URI createSearchUri(String jql, String includedFields, String expandFields, Integer maxResults, Integer startAt) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap();
        queryParams.put("jql", jql);
        if (maxResults != null) {
            queryParams.put("maxResults", String.valueOf(maxResults));
        }

        if (includedFields != null) {
            queryParams.put("fields", includedFields);
        }

        if (expandFields != null) {
            queryParams.put("expand", expandFields);
        }

        if (startAt != null) {
            queryParams.put("startAt", String.valueOf(startAt));
        }

        URI searchUri = buildURI(getBaseUri() + "search", queryParams);
        return searchUri;
    }

}
