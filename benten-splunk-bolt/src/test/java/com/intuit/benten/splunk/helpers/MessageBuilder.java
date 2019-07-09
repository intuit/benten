package com.intuit.benten.splunk.helpers;

import com.google.gson.JsonElement;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.splunk.actionhandlers.SplunkActionParameters;

import java.util.HashMap;

public class MessageBuilder {
    public static BentenMessage constructBentenUserLogsMessage(JsonElement authKey) {
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(SplunkActionParameters.PARAMETER_AUTHORISATION_CODE, authKey);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }

    public static BentenMessage constructSplunkHelpActionMessage(JsonElement helpItem) {
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(SplunkActionParameters.PARAMETER_SPLUNK_HELP, helpItem);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }
}