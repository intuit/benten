package com.intuit.benten.jira.actionhandlers;


import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.nlp.BentenMessage;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.HashMap;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@EnableAutoConfiguration
public class JiraCycleTimeActionHandlerTest extends JiraActionHandlerTest{

    @Autowired
    JiraCycleTimeActionHandler jiraCycleTimeActionHandler;

    @Test
    @Ignore
    public void testHandleRequest(){
        BentenMessage bentenMessage = new BentenMessage();

        JsonElement boardName = new JsonPrimitive("Combined_Services_Team_view");
        JsonElement noOfSprints = new JsonPrimitive("1");

        HashMap<String, JsonElement> parameters = new HashMap<>();
        parameters.put(JiraActionParameters.PARAMETER_BOARD_NAME,boardName);
        parameters.put(JiraActionParameters.PARAMETER_NO_OF_SPRINTS,noOfSprints);
        bentenMessage.setParameters(parameters);

        BentenHandlerResponse bentenHandlerResponse =
                jiraCycleTimeActionHandler.handle(bentenMessage);

        Assert.assertNotNull(bentenHandlerResponse.getBentenHtmlResponse());

    }

}
