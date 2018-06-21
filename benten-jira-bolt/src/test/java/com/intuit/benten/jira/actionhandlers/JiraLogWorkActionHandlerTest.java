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
public class JiraLogWorkActionHandlerTest extends JiraActionHandlerTest {

    @Autowired
    JiraLogWorkActionHandler jiraLogWorkActionHandler;

    @Autowired
    private BentenJiraClient bentenJiraClient;

    @Test
    public void testHandleRequest() throws InterruptedException {
        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        JsonElement time = new JsonPrimitive("3h 4m");
        JsonElement currentUser = new JsonPrimitive(TestHelper.assigneeName);
        //Thread.sleep(2000);
        BentenHandlerResponse bentenHandlerResponse =
                jiraLogWorkActionHandler.handle(MessageBuilder
                        .constructBentenLogWorkMessage(new JsonPrimitive(issueKey),time,currentUser));

        Issue issue = TestHelper.getIssueDetails(bentenJiraClient,issueKey);
        Assert.assertEquals(issue.getWorkLogs().get(0).getTimeSpent(),"3h 4m");
        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getSlackText().contains("Done! Logged"));
    }

    @Test
    public void testHandleRequestInvalidIssueKey() throws InterruptedException {
        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        JsonElement time = new JsonPrimitive("3h 4m");
        JsonElement currentUser = new JsonPrimitive(TestHelper.assigneeName);
        Thread.sleep(2000);
        BentenHandlerResponse bentenHandlerResponse =
                jiraLogWorkActionHandler.handle(MessageBuilder
                        .constructBentenLogWorkMessage(new JsonPrimitive("INVALID-1001"),time,currentUser));


        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse().getSlackText());
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getSlackText().contains("Issue Does Not Exist"));
    }

}
