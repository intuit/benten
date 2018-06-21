package com.intuit.benten.common.helpers;

import com.google.gson.JsonElement;
import com.intuit.benten.common.nlp.BentenMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class BentenMessageHelper {
    public static String getParameterAsString(BentenMessage bentenMessage, String parameterName){
        HashMap<String,JsonElement> parameters = bentenMessage.getParameters();
        if(parameters.containsKey(parameterName) && parameters.get(parameterName)!=null){
            return parameters.get(parameterName).getAsString();
        }
        return null;
    }
    public static Integer getParameterAsInteger(BentenMessage bentenMessage, String parameterName){
        HashMap<String,JsonElement> parameters = bentenMessage.getParameters();
        if(parameters.containsKey(parameterName) && parameters.get(parameterName)!=null){
            return parameters.get(parameterName).getAsInt();
        }
        return null;
    }
    public static String getParameterAsString(Map<String,JsonElement> parameters, String parameterName){
        if(parameters.containsKey(parameterName) && parameters.get(parameterName)!=null){
            return parameters.get(parameterName).getAsString();
        }
        return null;
    }
}
