package com.intuit.benten.jira.actionhandlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.jira.helpers.MessageBuilder;
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
public class JiraAssignIssuesToUserActionHandlerTest extends JiraActionHandlerTest {

    @Autowired
    JiraAssignIssueToUserActionHandler jiraAssignIssueToUserActionHandler;

    @Autowired
    BentenJiraClient bentenJiraClient;

    @Test
    public void testHandleRequest() throws InterruptedException {

        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);

        JsonElement currentUser = new JsonPrimitive(TestHelper.assigneeName);

        Thread.sleep(2000);

        BentenHandlerResponse bentenHandlerResponse =
                jiraAssignIssueToUserActionHandler.handle(MessageBuilder.constructBentenAssignIssueMessage(new JsonPrimitive(issueKey),currentUser));

        Issue issue = TestHelper.getIssueDetails(bentenJiraClient,issueKey);
        Assert.assertEquals(TestHelper.assigneeName,issue.getAssignee().getName());

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getSlackText().contains(TestHelper.assigneeName));
    }


}
