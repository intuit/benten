package com.intuit.benten.jira.actionhandlers;


import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.helpers.MessageBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */

@EnableAutoConfiguration
public class JiraVelocityActionHandlerTest extends JiraActionHandlerTest {

    @Autowired
    JiraSprintVelocityActionHandler jiraSprintVelocityActionHandler;

    @Test
    public void testHandleRequest(){
        BentenMessage bentenMessage;

        JsonElement boardName = new JsonPrimitive("Combined_Services_Team_view");
        JsonElement noOfSprints = new JsonPrimitive("1");

        bentenMessage =
                MessageBuilder.constructBentenSprintMessage(boardName,noOfSprints);
        BentenHandlerResponse bentenHandlerResponse =
                jiraSprintVelocityActionHandler.handle(bentenMessage);

        Assert.assertNotNull(bentenHandlerResponse.getBentenHtmlResponse());
    }

}
