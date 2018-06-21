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

import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@EnableAutoConfiguration
public class JiraSearchIssuesByUserActionHandlerTest extends JiraActionHandlerTest {


    @Autowired
    JiraSearchIssuesByUserActionHandler jiraSearchIssuesByUserActionHandler;

    @Autowired
    BentenJiraClient bentenJiraClient;

    @Test
    public void testHandleRequestCurrentUser(){

        JsonElement currentUser = new JsonPrimitive(TestHelper.assigneeName);

        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);

        String anotherIssueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,anotherIssueKey);

        BentenHandlerResponse bentenHandlerResponse =
                jiraSearchIssuesByUserActionHandler.handle(MessageBuilder.constructBentenMessage(currentUser,null));


        List<Issue> issues = bentenJiraClient.searchIssuesByAssignee(TestHelper.assigneeName,TestHelper.expandedFields,10);
        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse().getSlackText());
        Assert.assertTrue(issues.size() > 0);
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getSlackText().contains(TestHelper.project_key+"-1"));
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getSlackText().contains(TestHelper.project_key+"-2"));


    }

    @Test
    public void testHandleRequestUserOfInterest(){

        JsonElement userOfInterest = new JsonPrimitive("dungatla");

        JsonElement currentUser = new JsonPrimitive(TestHelper.assigneeName);

        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);

        String anotherIssueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,anotherIssueKey);

        BentenHandlerResponse bentenHandlerResponse =
                jiraSearchIssuesByUserActionHandler.handle(MessageBuilder.constructBentenMessage(currentUser,userOfInterest));

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse().getSlackText());
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getSlackText().contains(issueKey));
        Assert.assertTrue(bentenHandlerResponse.getBentenSlackResponse().getSlackText().contains(anotherIssueKey));
    }

}
