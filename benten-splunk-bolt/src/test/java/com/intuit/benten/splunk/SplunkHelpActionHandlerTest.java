package com.intuit.benten.splunk;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.splunk.actionhandlers.SplunkHelpActionHandler;
import com.intuit.benten.splunk.helpers.MessageBuilder;
import com.intuit.benten.splunk.properties.SplunkProperties;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {SplunkHelpActionHandler.class, SplunkHttpClient.class, SplunkProperties.class})
public class SplunkHelpActionHandlerTest {
    private static final String helpResponse = "Hi, in order to get the info of a user, please enter an application id";
    private static ClientAndServer mockServer;
    @Autowired
    SplunkHelpActionHandler splunkHelpActionHandler;

    @BeforeClass
    public static void setUpMockServer() {
        mockServer = startClientAndServer(8089);
    }

    @AfterClass
    public static void detachMockServer() {
        mockServer.stop();
    }

    @Test
    public void testSplunkHelpActionHandler() {
        JsonElement jsonElement = new JsonPrimitive(helpResponse);
        BentenHandlerResponse bentenHandlerResponse = splunkHelpActionHandler.handle(MessageBuilder.constructSplunkHelpActionMessage(jsonElement));

        //debug statement
        System.out.println(bentenHandlerResponse.getBentenSlackResponse().getSlackText());

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse().getSlackText());
    }
}
