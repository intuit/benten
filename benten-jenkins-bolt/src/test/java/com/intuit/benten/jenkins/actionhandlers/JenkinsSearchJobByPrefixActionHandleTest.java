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
 * Created by sshashidhar on 2/24/18.
 */

@EnableAutoConfiguration
public class JenkinsSearchJobByPrefixActionHandleTest extends BaseJenkinsTest {

    @Autowired
    JenkinsSearchJobByPrefixActionHandler jenkinsSearchJobByPrefixActionHandler;

    @Test
    public void testHandleRequest(){
        JsonElement jsonElement = new JsonPrimitive("DataConversions");

        BentenHandlerResponse bentenHandlerResponse =
                jenkinsSearchJobByPrefixActionHandler.handle(JenkinsMessageBuilder.constructBentenMessage(jsonElement));

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
    }
}
