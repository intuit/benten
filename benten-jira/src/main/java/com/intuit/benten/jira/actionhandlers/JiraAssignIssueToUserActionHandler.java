package com.intuit.benten.jira.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.constants.SlackConstants;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.jira.http.JiraHttpHelper;
import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.jira.model.User;
import com.intuit.benten.jira.utils.SlackMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ActionHandler(action = JiraActions.ACTION_JIRA_ASSIGN_ISSUE)
public class JiraAssignIssueToUserActionHandler implements BentenActionHandler {

    @Autowired
    private BentenJiraClient bentenJiraClient;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String issueKey = BentenMessageHelper
                .getParameterAsString(bentenMessage,JiraActionParameters.PARAMETER_ISSUE_KEY);
        String assigneeName= BentenMessageHelper
                .getParameterAsString(bentenMessage, SlackConstants.USER_OF_INTEREST);

        User assignee = new User();
        assignee.setName(assigneeName);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();

        try {
            bentenJiraClient.assignIssueToUser(issueKey, assignee);
            Issue issue = bentenJiraClient
                    .getIssueDetails(issueKey, JiraConverter.CommonExpandedFields);
            BentenSlackResponse bentenSlackResponse = SlackMessageRenderer.renderIssueDetails(issue);
            String text = SlackFormatter.create()
                    .text("Assigned issue ")
                    .link(JiraHttpHelper.browseIssueUri(issueKey).toString(), issueKey)
                    .text(" to ")
                    .code(assigneeName)
                    .text("!")
                    .build();
            bentenSlackResponse.setSlackText(text);
            bentenHandlerResponse.setBentenSlackResponse(bentenSlackResponse);
        }catch(BentenJiraException e){
            bentenHandlerResponse
                    .setBentenSlackResponse(SlackMessageRenderer.errorMessage(e.getMessage().substring(e.getMessage().lastIndexOf(":")+1)));
        }catch (Exception e){
            throw new BentenJiraException(e.getMessage());
        }

        return bentenHandlerResponse;
    }
}
