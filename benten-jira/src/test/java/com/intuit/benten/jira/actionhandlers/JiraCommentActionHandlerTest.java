package com.intuit.benten.jira.actionhandlers;


import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.jira.helpers.MessageBuilder;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.jira.helpers.TestHelper;
import com.intuit.benten.jira.model.Issue;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */

@EnableAutoConfiguration
public class JiraCommentActionHandlerTest extends JiraActionHandlerTest{

    @Autowired
    JiraCommentActionHandler jiraCommentActionHandler;

    @Autowired
    BentenJiraClient bentenJiraClient;

    @Test
    public void testHandleRequest() throws InterruptedException {

        JsonElement comment = new JsonPrimitive(TestHelper.comment);
        JsonElement currentUser = new JsonPrimitive(TestHelper.assigneeName);


        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        Thread.sleep(2000);
        BentenHandlerResponse bentenHandlerResponse =
                jiraCommentActionHandler.handle(MessageBuilder
                        .constructBentenCommentMessage(new JsonPrimitive(issueKey),comment,currentUser));
        Thread.sleep(1000);
        Issue issue = TestHelper.getIssueDetails(bentenJiraClient,issueKey);
        Assert.assertTrue(issue.getComments().get(0).getBody().contains(TestHelper.comment));

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse().getSlackText());
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getSlackText().contains("commented on"));
    }


    @Test
    public void testHandleRequestInvalidIssueKey() throws InterruptedException {

        JsonElement comment = new JsonPrimitive(TestHelper.comment);
        JsonElement currentUser = new JsonPrimitive(TestHelper.assigneeName);

        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        Thread.sleep(2000);
        BentenHandlerResponse bentenHandlerResponse =
                jiraCommentActionHandler.handle(MessageBuilder
                        .constructBentenCommentMessage(new JsonPrimitive("INVALID-123"),comment,currentUser));

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse().getSlackText());
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getSlackText().contains("Issue Does Not Exist"));
    }
}
