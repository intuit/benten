package com.intuit.benten.hackernews.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.benten.common.http.HttpHelper;
import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;
import com.intuit.benten.hackernews.model.HackernewsItem;
import com.intuit.benten.hackernews.properties.HackernewsProperties;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by jleveroni on 10/09/2019
 */
public class HackernewsService {
    public static class HACKERNEWS_ENDPOINTS {
        public static final String TOP_STORIES = "topstories.json";
        public static final String NEW_STORIES = "newstories.json";
        public static final String BEST_STORIES = "beststories.json";
        public static final String LATEST_ASKS = "askstories.json";
        public static final String LATEST_SHOW = "showstories.json";
        public static final String LATEST_JOB = "jobstories.json";
        public static final String MAX_ITEM = "maxitem.json";
    }

    private static final Logger logger = LoggerFactory.getLogger(HackernewsService.class);

    @Autowired
    private HttpHelper httpHelper;

    @Autowired
    private HackernewsProperties hackernewsProperties;

    private ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

    public HackernewsItem getTopStories() { return request(HACKERNEWS_ENDPOINTS.TOP_STORIES); }
    public HackernewsItem getNewStories() { return request(HACKERNEWS_ENDPOINTS.NEW_STORIES); }
    public HackernewsItem getBestStories() { return request(HACKERNEWS_ENDPOINTS.BEST_STORIES); }
    public HackernewsItem getLatestAsks() { return request(HACKERNEWS_ENDPOINTS.LATEST_ASKS); }
    public HackernewsItem getLatestShowStories() { return request(HACKERNEWS_ENDPOINTS.LATEST_SHOW); }
    public HackernewsItem getLatestJobStories() { return request(HACKERNEWS_ENDPOINTS.LATEST_JOB); }
    public HackernewsItem getNewestContentId() { return request(HACKERNEWS_ENDPOINTS.MAX_ITEM); }

    private HackernewsItem request(String endpoint) throws BentenHackernewsException { return request(endpoint, true); }

    private HackernewsItem request(String endpoint, boolean prettyPrintresponse) throws BentenHackernewsException {
        String uri = buildHackernewsRequestUrl(prettyPrintresponse, endpoint);
        HttpGet req = new HttpGet(uri);

        try {
            HttpResponse res = httpHelper.getClient().execute(req);

            if (res.getStatusLine().getStatusCode() != 200) {
                handleFailedHackerNewsRequest(res);
            }

            String json = EntityUtils.toString(res.getEntity());
            return objectMapper.readValue(json, HackernewsItem.class);
        } catch (IOException e) {
            throw new BentenHackernewsException(e.getMessage());
        }
    }

    private String buildHackernewsRequestUrl(boolean prettyPrint, String ... pathSegments) {
        StringBuilder sb = new StringBuilder(hackernewsProperties.getUrlWithApiVersion());

        if (pathSegments != null) {
            for (String segment : pathSegments) {
                sb.append("/").append(segment);
            }
        }

        if (prettyPrint) {
            sb.append("?print=pretty");
        }

        return sb.toString();
    }

    private void handleFailedHackerNewsRequest(HttpResponse response) {
        throw new NotImplementedException();
    }
}
