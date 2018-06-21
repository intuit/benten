package com.intuit.benten.jira.helpers;

import com.google.gson.JsonElement;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.actionhandlers.JiraActionParameters;
import com.intuit.benten.common.constants.SlackConstants;

import java.util.HashMap;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class MessageBuilder {

    public static BentenMessage constructBentenMessage(JsonElement currentUser, JsonElement userOfInterest) {
        BentenMessage bentenMessage = new BentenMessage();
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        parameters.put(SlackConstants.CURRENT_USER,currentUser);
        parameters.put(SlackConstants.USER_OF_INTEREST,userOfInterest);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }


    public static BentenMessage constructBentenAssignIssueMessage(JsonElement issueKey, JsonElement assignee){
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(JiraActionParameters.PARAMETER_ISSUE_KEY,issueKey);
        parameters.put(SlackConstants.USER_OF_INTEREST,assignee);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }

    public static BentenMessage constructBentenLogWorkMessage(JsonElement issueKey, JsonElement time, JsonElement currentUser){
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(JiraActionParameters.PARAMETER_ISSUE_KEY,issueKey);
        parameters.put(JiraActionParameters.PARAMETER_TIME,time);
        parameters.put(SlackConstants.CURRENT_USER,currentUser);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }

    public static BentenMessage constructBentenTransitionIssueMessage(JsonElement issueKey, JsonElement status, JsonElement currentUser){
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(JiraActionParameters.PARAMETER_ISSUE_KEY,issueKey);
        parameters.put(JiraActionParameters.PARAMETER_STATUS,status);
        parameters.put(SlackConstants.CURRENT_USER,currentUser);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }

    public static BentenMessage constructBentenCreateIssueMessage(JsonElement projectKey, JsonElement currentUser, JsonElement userOfInterest,
                                                                  JsonElement summary, JsonElement issueType) {
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(SlackConstants.PROJECT_KEY, projectKey);
        parameters.put(SlackConstants.USER_OF_INTEREST, userOfInterest);
        parameters.put(SlackConstants.CURRENT_USER, currentUser);
        parameters.put(SlackConstants.SUMMARY, summary);
        parameters.put(SlackConstants.ISSUE_TYPE,issueType);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }

    public static BentenMessage constructBentenCommentMessage(JsonElement issueKey, JsonElement comment, JsonElement currentUser){
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(JiraActionParameters.PARAMETER_ISSUE_KEY,issueKey);
        parameters.put(JiraActionParameters.PARAMETER_COMMENT,comment);
        parameters.put(SlackConstants.CURRENT_USER,currentUser);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }

    public static BentenMessage constructBentenSprintMessage(JsonElement boardName, JsonElement noOfSprints){
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(JiraActionParameters.PARAMETER_BOARD_NAME,boardName);
        parameters.put(JiraActionParameters.PARAMETER_NO_OF_SPRINTS,noOfSprints);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }
    public static BentenMessage constructBentenCreateIssueCatalystMessage(JsonElement projectKey, JsonElement issueType){
        HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
        BentenMessage bentenMessage = new BentenMessage();
        parameters.put(SlackConstants.PROJECT_KEY,projectKey);
        parameters.put(SlackConstants.ISSUE_TYPE,issueType);
        bentenMessage.setParameters(parameters);
        return bentenMessage;
    }

}
