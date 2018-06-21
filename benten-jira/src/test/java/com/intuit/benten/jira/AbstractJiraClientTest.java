package com.intuit.benten.jira;

import com.intuit.benten.jira.BentenJiraClient;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public abstract class AbstractJiraClientTest {
    
    protected abstract BentenJiraClient getClient();

//    @Test
//    public void testSearchIssuesByAssignee() {
//        int numberOfIssues = 10;
//        String assignee = "dungatla";
//
//        String expandedFields = Field.ASSIGNEE
//                .concat("," + Field.STATUS)
//                .concat("," + Field.PRIORITY)
//                .concat("," + Field.ISSUE_TYPE)
//                .concat("," + Field.PRIORITY)
//                .concat("," + Field.SUMMARY);
//        List<Issue> issues
//                = getClient().searchIssuesByAssignee(assignee, expandedFields, numberOfIssues);
//
//        Assert.assertEquals(issues.size(), numberOfIssues);
//        Assert.assertEquals(issues.get(0).getAssignee().getName().toString(), assignee);
//        Assert.assertTrue(issues.get(0).getSummary() instanceof String);
//    }
//
//    @Test
//    public void testAssigneeIssue() {
//
//        String assignee = "dungatla";
//        String issueKey = "PSAA-1001";
//
//        User user = new User();
//        user.setName(assignee);
//
//        getClient().assignIssueToUser(issueKey, user);
//        Issue issue = getClient().getIssueDetails(issueKey, JiraConverter.CommonExpandedFields);
//        Assert.assertEquals(assignee, issue.getAssignee().getName().toString());
//        Assert.assertEquals(issueKey, issue.getKey());
//
//    }
//
//    @Test
//    public void testGetIssueDetails() {
//
//        String assignee = "dungatla";
//        String issueKey = "PSAA-1002";
//
//        String expandedFields = Field.ASSIGNEE
//                .concat("," + Field.STATUS)
//                .concat("," + Field.PRIORITY)
//                .concat("," + Field.ISSUE_TYPE)
//                .concat("," + Field.PRIORITY)
//                .concat("," + Field.SUMMARY);
//
//        Issue issue = getClient().getIssueDetails(issueKey, expandedFields);
//
//        Assert.assertEquals(assignee, issue.getAssignee().getName().toString());
//        Assert.assertEquals(issueKey, issue.getKey());
//
//    }
//
//    @Test
//    public void testLogWork() {
//        String onBehalfOf = "dungatla";
//        String issueKey = "PSAA-1009";
//        String time = "3h 4m";
//        String comment = "did analysis";
//        Issue issueBeforeLogging = getClient().getIssueDetails(issueKey, JiraConverter.CommonExpandedFields);
//        getClient().logWork(issueKey, time, onBehalfOf, comment);
//        Issue issueAfterLogging = getClient().getIssueDetails(issueKey, JiraConverter.CommonExpandedFields);
//        // peter TODO
//        // Assert.assertEquals(issueBeforeLogging.getWorkLogs().size() + 1, issueAfterLogging.getWorkLogs().size());
//    }
//
//    @Test
//    public void testComment() {
//        String onBehalfOf = "dungatla";
//        String issueKey = "PSAA-1009";
//        String comment = "this is a different comment";
//        getClient().comment(issueKey, comment, onBehalfOf);
//        Issue issue = getClient().getIssueDetails(issueKey, JiraConverter.CommonExpandedFields);
//        Assert.assertTrue(issue.getComments().get(issue.getComments().size() - 1).getBody().contains(comment));
//    }

}
