package com.intuit.benten.jenkins.actionhandlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.jenkins.helpers.JenkinsMessageBuilder;
import com.intuit.benten.jenkins.BaseJenkinsTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * Created by sshashidhar on 2/25/18.
 */

@EnableAutoConfiguration
public class JenkinsJobDetailsByJobNameActionHandlerTest extends BaseJenkinsTest{

    @Autowired
    JenkinsJobDetailsByJobNameActionHandler jenkinsJobDetailsByJobNameActionHandler;

    @Test
    public void testHandleRequest(){
        JsonElement jsonElement = new JsonPrimitive("DataConversions-Env-Stability");

        BentenHandlerResponse bentenHandlerResponse =
                jenkinsJobDetailsByJobNameActionHandler.handle(JenkinsMessageBuilder.constructBentenMessageForJobName(jsonElement));

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
    }
}
