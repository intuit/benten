package com.intuit.benten.flickr.helpers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.flickr.actionhandlers.FlickrActionParamaters;

import java.util.HashMap;

/**
 * Created by itstc on 2019-10-15
 */
public class FlickrMessageBuilder {
    public static BentenMessage constructBrandCamerasMessage(String brandName) {
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(FlickrActionParamaters.PARAMETER_BRAND_NAME, new JsonPrimitive(brandName));
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }
    public static BentenMessage constructSearchPhotosMessage(String mediaType, String searchValue, String searchLimit) {
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(FlickrActionParamaters.PARAMETER_MEDIA_TYPE, new JsonPrimitive(mediaType));
        parameters.put(FlickrActionParamaters.PARAMETER_SEARCH_VALUE, new JsonPrimitive(searchValue));
        parameters.put(FlickrActionParamaters.PARAMETER_SEARCH_LIMIT, new JsonPrimitive(searchLimit));
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }
}
