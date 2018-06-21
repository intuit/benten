package com.intuit.benten.jira;

import com.intuit.benten.jira.http.JiraAgileHttpClient;
import com.intuit.benten.jira.http.JiraGhClient;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.jira.http.JiraHttpClient;
import com.intuit.benten.jira.model.Transition;
import com.intuit.benten.jira.model.ghc.Sprint;
import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.jira.model.User;
import com.intuit.benten.jira.model.agile.Board;
import com.intuit.benten.jira.model.ghc.SprintReport;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class BentenJiraClient {

    private static final Logger logger = LoggerFactory.getLogger(BentenJiraClient.class);

    @Autowired
    private JiraHttpClient jiraHttpClient;

    @Autowired
    private JiraAgileHttpClient jiraAgileHttpClient;

    @Autowired
    private JiraGhClient jiraGhClient;


    @PostConstruct
    public void init(){
        try {


        }
        catch (Exception ex){
            logger.error(ex.getMessage(),ex);
        }
    }

    public List<Issue> searchIssuesByAssignee(String assignee, String expandedFields, int maxResults) throws BentenJiraException {

        try {
            String jql = "assignee="+assignee+" ORDER BY updated DESC";
            List<Issue> issues = jiraHttpClient.searchIssues(jql, expandedFields,maxResults);
            return issues ;
        }
        catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new BentenJiraException(ex.getMessage());
        }
    }

    public Issue getIssueDetails(String issueKey,String expandedFields) throws BentenJiraException {
        try {
            Issue issue = jiraHttpClient.getIssueDetails(issueKey,expandedFields);
            return issue;
        }
        catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new BentenJiraException(ex.getMessage());
        }
    }

    public void assignIssueToUser(String issueKey,User assignee) throws BentenJiraException {
        try {

            JSONObject fields= new JSONObject();
            String string = JiraConverter.objectMapper.writeValueAsString(assignee);
            fields.put("assignee",string);
            jiraHttpClient.updateIssue(issueKey,fields);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new BentenJiraException(ex.getMessage());
        }
    }

    public void updateAssigneeAndReporter(String issueKey,User assignee, User reporter) throws BentenJiraException {
        try {

            JSONObject fields= new JSONObject();
            String string = JiraConverter.objectMapper.writeValueAsString(assignee);
            String reporterString = JiraConverter.objectMapper.writeValueAsString(reporter);
            fields.put("assignee",string);
            fields.put("reporter",reporterString);
            jiraHttpClient.updateIssue(issueKey,fields);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new BentenJiraException(ex.getMessage());
        }
    }

    public void logWork(String issueKey,String time,String onBehalfOf, String comment) throws BentenJiraException {
        try {

              JSONObject worklog = new JSONObject();
              worklog.put("timeSpent",time);
              if(comment!=null) {
                  worklog.put("comment","logging on behalf of @"+onBehalfOf+"\n"+ comment);
              }
              jiraHttpClient.logWork(issueKey,worklog);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new BentenJiraException(ex.getMessage());
        }
    }

    public List<Transition> possibleTransitions(String issueKey) throws BentenJiraException {
        try {
            return jiraHttpClient.getPossibleTransitions(issueKey);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new BentenJiraException(ex.getMessage());
        }
    }


    public void transition(String issueKey, Transition transition) throws BentenJiraException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("transition",transition);
            jiraHttpClient.transitionIssue(issueKey,jsonObject);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new BentenJiraException(ex.getMessage());
        }
    }

    public void comment(String issueKey, String comment, String onBehalfOf) throws BentenJiraException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("body","comment on behalf of @"+onBehalfOf+"\n\n"+comment);
            jiraHttpClient.comment(issueKey,jsonObject);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new BentenJiraException(ex.getMessage());
        }
    }

    public String createIssue(JSONObject fields){
        try {
            return jiraHttpClient.createIssue(fields);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new BentenJiraException(ex.getMessage());
        }
    }

    public JSONObject getCreateMetadata(String projectKey, String issueType){
        try {
            return jiraHttpClient.getCreateMetaData(projectKey,issueType);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            throw new BentenJiraException(ex.getMessage());
        }
    }

    public List<Board> getBoardsByName(String boardName){
        List<Board> boards = jiraAgileHttpClient.boardsByName(boardName);
        return boards;
    }

    public List<Sprint> getSprintsByBoardId(String boardId){
        return jiraGhClient.sprints(boardId);
    }

    public List<Sprint> getSprintsByBoardName(String boardName){
        List<Board> boards = jiraAgileHttpClient.boardsByName(boardName);
        Board board;
        List<Sprint> sprints=null;
        if(boards!=null && !boards.isEmpty()){
            board = boards.get(0);
            sprints = jiraGhClient.sprints(board.getId());
        }
        return sprints;
    }

    public List<Issue> getSprintIssues(String sprintId){
        List<Issue> issues = jiraAgileHttpClient.sprintIssues(sprintId);
        return issues;
    }

    public SprintReport getSprintReport(String boardId,String sprintId){
        SprintReport sprintReport = jiraGhClient.sprintReport(boardId,sprintId);
        return sprintReport;
    }



}
