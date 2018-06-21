package com.intuit.benten.jira.helpers;

import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.jira.model.Field;
import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.jira.model.User;
import net.sf.json.JSONObject;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class TestHelper {

    public static final String project_key = "PSAA";
    public static final String issue_type = "Story";
    public static final String summary = "test-summary";
    public static final String description = "test-description";


    public static final String assigneeName = "dungatla";
    public static final String reporterName = "dungatla";

    public static final String comment = "test-comment";

    public static final String expandedFields = Field.ASSIGNEE
            .concat(","+Field.STATUS)
            .concat(","+Field.PRIORITY)
            .concat(","+Field.ISSUE_TYPE)
            .concat(","+Field.PRIORITY)
            .concat(","+Field.SUMMARY)
            .concat(","+Field.DESCRIPTION)
            .concat(","+Field.COMMENT);

    public static String createStory(BentenJiraClient bentenJiraClient) {

        JSONObject jsonObject = new JSONObject();
        JSONObject metaData = bentenJiraClient.getCreateMetadata(project_key, issue_type);
        jsonObject.put(Field.SUMMARY, summary);
        jsonObject.put(Field.DESCRIPTION, description);
        jsonObject.put(Field.PROJECT, JiraConverter.toJson(Field.PROJECT, project_key, metaData));
        jsonObject.put(Field.ISSUE_TYPE, JiraConverter.toJson(Field.ISSUE_TYPE, issue_type, metaData));

        String issueUrl = bentenJiraClient.createIssue(jsonObject);

        return issueUrl.substring(issueUrl.lastIndexOf("/") + 1);
    }

    public static void updateAssigneeAndReporter(BentenJiraClient bentenJiraClient,String issueKey){
        User assignee = new User();
        assignee.setName(assigneeName);
        User reporter = new User();
        reporter.setName(reporterName);
        reporter.setName(reporterName);
        bentenJiraClient.updateAssigneeAndReporter(issueKey,assignee,reporter);
    }

    public static Issue getIssueDetails(BentenJiraClient bentenJiraClient, String issueKey){

        return bentenJiraClient.getIssueDetails(issueKey,expandedFields);
    }

}

