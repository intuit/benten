package com.intuit.benten.common.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intuit.benten.common.nlp.BentenMessage;

import java.util.*;

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
    public static List<String> getParameterAsList(BentenMessage bentenMessage, String parameterName ){
        HashMap<String,JsonElement> parameters = bentenMessage.getParameters();
        List<String> arrayList = null;

        if(parameters.containsKey(parameterName) && parameters.get(parameterName)!=null){
            JsonArray jsonArray = parameters.get(parameterName).getAsJsonArray();
            arrayList = new ArrayList<>();
            for(int i=0;i < jsonArray.size();i++){
                arrayList.add(jsonArray.get(i).getAsString());
            }
        }
        return arrayList;
    }

    public static String getParameterAsString(Map<String,JsonElement> parameters, String parameterName){
        if(parameters.containsKey(parameterName) && parameters.get(parameterName)!=null){
            return parameters.get(parameterName).getAsString();
        }
        return null;
    }
}
