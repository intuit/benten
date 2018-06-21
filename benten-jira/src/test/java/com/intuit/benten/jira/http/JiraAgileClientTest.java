package com.intuit.benten.jira.http;

import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.jira.model.agile.Board;
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
public class JiraAgileClientTest extends JiraClientTest{

    @Autowired
    private JiraAgileHttpClient jiraAgileHttpClient;

    @Test
    public void testSprintsIssues() {
        String sprintid="71231";
        List<Issue> issues = jiraAgileHttpClient.sprintIssues(sprintid);
        Assert.assertTrue(issues.size()>0);
    }

    @Test
    public void testBoardsByName() {
        String boardName = "Combined_Services_Team_view";
        List<Board> boards = jiraAgileHttpClient.boardsByName(boardName);
        Assert.assertEquals(boardName,boards.get(0).getName());
    }
}