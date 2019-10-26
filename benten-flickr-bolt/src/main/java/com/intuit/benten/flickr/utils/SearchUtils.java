package com.intuit.benten.flickr.utils;

import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Created by itstc on 2019-10-11
 */
public class SearchUtils {
    public static String generateGoogleSearchUrl(String searchString) {
        try {
            String uriQuery = UriUtils.encode(searchString, StandardCharsets.UTF_8.name());
            return SearchItems.GOOGLE_SEARCH_URL + uriQuery;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
