package com.intuit.benten.jira.actionhandlers;


import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackAttachment;
import com.intuit.benten.common.actionhandlers.BentenSlackField;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.jira.helpers.TestHelper;
import com.intuit.benten.jira.model.Field;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */

@EnableAutoConfiguration
public class JiraSearchIssuesDetailsActionHandlerTest extends JiraActionHandlerTest {

    @Autowired
    JiraIssueDetailsActionHandler jiraIssueDetailsActionHandler;

    @Autowired
    private BentenJiraClient bentenJiraClient;

    @Test
    public void testHandleRequest(){
        BentenMessage bentenMessage = new BentenMessage();


        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();

        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        JsonElement jsonElement = new JsonPrimitive(issueKey);
        parameters.put(JiraActionParameters.PARAMETER_ISSUE_KEY,jsonElement);
        bentenMessage.setParameters(parameters);

        BentenHandlerResponse bentenHandlerResponse =
                jiraIssueDetailsActionHandler.handle(bentenMessage);

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getBentenSlackAttachments().size()>0);
        BentenSlackAttachment bentenSlackAttachment  = bentenHandlerResponse.getBentenSlackResponse().getBentenSlackAttachments().get(0);

        Assert.assertTrue(bentenSlackAttachment.getText().contains("test-summary"));
        Iterator it = bentenSlackAttachment.getBentenSlackFields().iterator();
        long fields =0;
        while(it.hasNext()){
           BentenSlackField bentenSlackField = (BentenSlackField) it.next();

           switch (bentenSlackField.getTitle().toLowerCase()){
               case Field.ASSIGNEE: Assert.assertEquals(TestHelper.assigneeName,bentenSlackField.getValue()); fields++; break;
               case Field.ISSUE_TYPE : Assert.assertEquals(TestHelper.issue_type,bentenSlackField.getValue()); fields++; break;
               case Field.STATUS : Assert.assertEquals("New",bentenSlackField.getValue()); fields++; break;
               case Field.DESCRIPTION : Assert.assertEquals(TestHelper.description,bentenSlackField.getValue().toString()); fields++; break;
           }
        }
        Assert.assertEquals(4,fields);
    }

    @Test
    public void testHandleRequestIssueNotFound(){
        BentenMessage bentenMessage = new BentenMessage();


        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();

        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        JsonElement jsonElement = new JsonPrimitive("INVALID-1");
        parameters.put(JiraActionParameters.PARAMETER_ISSUE_KEY,jsonElement);
        bentenMessage.setParameters(parameters);

        BentenHandlerResponse bentenHandlerResponse =
                jiraIssueDetailsActionHandler.handle(bentenMessage);

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse().getSlackText());
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getSlackText().contains("Issue Does Not Exist"));

    }






}
