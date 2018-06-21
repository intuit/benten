package com.intuit.benten.jira.http;

import com.intuit.benten.jira.model.ghc.Sprint;
import com.intuit.benten.jira.model.ghc.SprintReport;
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
public class JiraGhClientTest extends JiraClientTest{

    @Autowired
    private JiraGhClient jiraGhClient;

    @Test
    public void testSprints(){
        String boarId = "15493";
        List<Sprint> sprints = jiraGhClient.sprints(boarId);
        Assert.assertTrue(sprints.size()>0);
    }

    @Test
    public void testSprintReport(){
        String boarId = "15493";
        String sprintId = "71231";
        SprintReport sprintReport = jiraGhClient.sprintReport(boarId,sprintId);
        Assert.assertEquals(sprintId,sprintReport.getSprint().getId());
    }
}
