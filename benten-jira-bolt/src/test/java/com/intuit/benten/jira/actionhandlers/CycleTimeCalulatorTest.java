package com.intuit.benten.jira.actionhandlers;

import com.intuit.benten.jira.actionhandlers.helpers.CycleTimeCalculator;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.jira.model.ghc.Sprint;
import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.jira.model.bentenjira.StoryCycleTime;
import com.intuit.benten.jira.model.ghc.SprintReport;
import net.sf.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class CycleTimeCalulatorTest {

    @Test
    @Ignore
    public void testCycleTimeCalculator() throws IOException {
        String json = "{}";
        JSONObject jsonObject = JiraConverter.objectMapper.readValue(json, JSONObject.class);
        Issue issue = new Issue(jsonObject);
        Sprint sprint = new Sprint();
        sprint.setStartDate(new Date("2/5/18 07:30 PM"));
        sprint.setEndDate(new Date("2/18/18 03:30 AM"));
        sprint.setCompleteDate(new Date("2/19/18 10:23 PM"));
        SprintReport sprintReport=new SprintReport();
        sprintReport.setSprint(sprint);
        StoryCycleTime storyCycleTime =new CycleTimeCalculator().storyCycleTime(issue,sprintReport);
        float devCycleTime = storyCycleTime.getDevCycleTime();
        float releaseCycleTime = storyCycleTime.getReleaseCycleTime();
    }

}
