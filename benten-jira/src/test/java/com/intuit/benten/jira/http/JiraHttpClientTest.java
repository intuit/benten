package com.intuit.benten.jira.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.jira.helpers.TestHelper;
import com.intuit.benten.jira.model.Field;
import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.jira.model.Transition;
import com.intuit.benten.jira.model.User;
import net.sf.json.JSONObject;
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
public class JiraHttpClientTest extends JiraClientTest {

    @Autowired
    private JiraHttpClient jiraHttpClient;

    @Autowired
    private BentenJiraClient bentenJiraClient;

    @Test
    public void testGetIssueDetails(){

        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        String expandedFields = Field.ASSIGNEE
                .concat(","+Field.STATUS)
                .concat(","+Field.PRIORITY)
                .concat(","+Field.ISSUE_TYPE)
                .concat(","+Field.PRIORITY)
                .concat(","+Field.SUMMARY)
                .concat(","+Field.DUE_DATE)
                .concat(","+Field.COMMENT)
                .concat(","+Field.DUE_DATE)
                .concat(","+Field.FIX_VERSIONS)
                .concat(","+Field.CHANGE_LOG)
                .concat(","+Field.CREATED_DATE)
                .concat(","+Field.UPDATED_DATE)
                .concat(","+Field.RESOLUTION_DATE)
                .concat(","+Field.WATCHES)
                .concat(","+Field.VOTES)
                .concat(","+Field.ATTACHMENT)
                .concat(","+Field.COMPONENTS)
                .concat(","+Field.DESCRIPTION)
                .concat(","+Field.ISSUE_LINKS)
                .concat(","+Field.LABELS)
                .concat(","+Field.PROJECT)
                .concat(","+Field.REPORTER)
                .concat(","+Field.RESOLUTION)
                .concat(","+Field.TIME_TRACKING)
                .concat(","+Field.VERSIONS)
                .concat(","+Field.WORKLOG)
                .concat(","+Field.TIME_ESTIMATE)
                .concat(","+Field.TIME_SPENT)
                .concat(","+Field.SECURITY);

        Issue issue = jiraHttpClient.getIssueDetails(issueKey,expandedFields);

        Assert.assertEquals(TestHelper.assigneeName,issue.getAssignee().getName().toString());
        Assert.assertEquals(issueKey,issue.getKey());
    }


    @Test
    public void testUpdateIssue() throws JsonProcessingException, InterruptedException {
        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        JSONObject fields= new JSONObject();
        User assignee = new User();
        assignee.setName(TestHelper.assigneeName);
        String string = JiraConverter.objectMapper.writeValueAsString(assignee);
        fields.put("assignee",string);
        Thread.sleep(2000);
        jiraHttpClient.updateIssue(issueKey,fields);
        Issue issue = jiraHttpClient.getIssueDetails(issueKey,JiraConverter.CommonExpandedFields);
        Assert.assertEquals(TestHelper.assigneeName,issue.getAssignee().getName().toString());
        Assert.assertEquals(issueKey,issue.getKey());
    }

    @Test
    public void testLogWork() throws JsonProcessingException, InterruptedException {
        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        String time = "3h 5m";
        JSONObject worklog= new JSONObject();
        worklog.put("timeSpent",time);
        worklog.put("comment","this is a comment");
        Thread.sleep(2000);
        jiraHttpClient.logWork(issueKey,worklog);
        Issue issueAfterLogging = jiraHttpClient.getIssueDetails(issueKey,JiraConverter.CommonExpandedFields);
        Assert.assertEquals(1,issueAfterLogging.getWorkLogs().size());

    }

    @Test
    public void testGetPossibleTransitions() throws JsonProcessingException {
        String issueKey = "PSAA-1009";
        List<Transition> transitions = jiraHttpClient.getPossibleTransitions(issueKey);
    }

    @Test
    public void testTransitionIssue() throws JsonProcessingException {
        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        JSONObject transition = new JSONObject();
        List<Transition> transitions = jiraHttpClient.getPossibleTransitions(issueKey);
        transition.put("transition",transitions.get(0));
        jiraHttpClient.transitionIssue(issueKey,transition);
    }

    @Test
    public void testComment() throws InterruptedException {
        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        String comment = "this is a new comment";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("body",comment);
        Thread.sleep(2000);
        jiraHttpClient.comment(issueKey,jsonObject);
        Issue issue = jiraHttpClient.getIssueDetails(issueKey,JiraConverter.CommonExpandedFields);
        Assert.assertEquals(comment,issue.getComments().get(issue.getComments().size()-1).getBody());
    }

    @Test
    public void testGetCreateMetaData(){

        String projectKey = "PSAA";
        String issueType = "Story";

        JSONObject fields= jiraHttpClient.getCreateMetaData(projectKey,issueType);

        Assert.assertNotNull(fields);

    }

    @Test
    public void testCreateIssue(){

        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);
        JSONObject jsonObject = new JSONObject();

        JSONObject metaData= jiraHttpClient.getCreateMetaData(TestHelper.project_key,TestHelper.issue_type);
        jsonObject.put("summary","test");
        jsonObject.put(Field.DESCRIPTION,"test-description");
        jsonObject.put(Field.PROJECT,JiraConverter.toJson(Field.PROJECT,TestHelper.project_key,metaData));
        jsonObject.put(Field.ISSUE_TYPE ,JiraConverter.toJson(Field.ISSUE_TYPE,TestHelper.issue_type,metaData));
        String issueUri = jiraHttpClient.createIssue(jsonObject);
        Assert.assertNotNull(issueUri);

    }

    @Test
    public void testSearchIssues() throws InterruptedException {

        String issueKey = TestHelper.createStory(bentenJiraClient);
        TestHelper.updateAssigneeAndReporter(bentenJiraClient,issueKey);

        String jql = "assignee="+TestHelper.assigneeName+" ORDER BY updated DESC";
        int maxResults = 10;
        String expandedFields = Field.ASSIGNEE
                .concat(","+Field.STATUS)
                .concat(","+Field.PRIORITY)
                .concat(","+Field.ISSUE_TYPE)
                .concat(","+Field.PRIORITY)
                .concat(","+Field.SUMMARY);

        List<Issue> issues = jiraHttpClient.searchIssues(jql,expandedFields,maxResults);
        Assert.assertTrue(issues.size() > 0);
        Assert.assertEquals(issues.get(0).getAssignee().getName().toString(),TestHelper.assigneeName);
        Assert.assertTrue(issues.get(0).getSummary() instanceof String);
    }
}
