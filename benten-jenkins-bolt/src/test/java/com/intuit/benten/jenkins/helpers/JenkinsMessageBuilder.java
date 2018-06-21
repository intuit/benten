package com.intuit.benten.jenkins.helpers;

import com.google.gson.JsonElement;
import com.intuit.benten.jenkins.actionhandlers.JenkinsActionParameters;
import com.intuit.benten.common.nlp.BentenMessage;

import java.util.HashMap;

/**
 * Created by sshashidhar on 2/24/18.
 */
public class JenkinsMessageBuilder {

    public static BentenMessage constructBentenMessage(JsonElement jobPrefix){
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(JenkinsActionParameters.PARAMETER_JOB_PREFIX,jobPrefix);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }

    public static BentenMessage constructBentenMessageForJobName(JsonElement jobName){
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(JenkinsActionParameters.PARAMETER_JOB_JOBNAME,jobName);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }
}
