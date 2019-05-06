package com.intuit.benten.jira.actionhandlers;

import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.jira.model.Field;
import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.utils.SlackMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ActionHandler(action = JiraActions.ACTION_JIRA_ISSUE_DETAILS)
public class JiraIssueDetailsActionHandler implements BentenActionHandler {

    @Autowired
    private BentenJiraClient bentenJiraClient;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String issueKey = BentenMessageHelper.getParameterAsString(bentenMessage,JiraActionParameters.PARAMETER_ISSUE_KEY);

        String expandedFields = Field.ASSIGNEE
                .concat(","+Field.STATUS)
                .concat(","+Field.PRIORITY)
                .concat(","+Field.ISSUE_TYPE)
                .concat(","+Field.PRIORITY)
                .concat(","+Field.SUMMARY)
                .concat(","+Field.DESCRIPTION)
                .concat(","+Field.COMMENT);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();

        try {

            Issue issue = bentenJiraClient.getIssueDetails(issueKey, expandedFields);
            bentenHandlerResponse.setBentenSlackResponse(SlackMessageRenderer.renderIssueDetails(issue));
        }catch(BentenJiraException e){
            bentenHandlerResponse
                    .setBentenSlackResponse(SlackMessageRenderer.errorMessage(e.getMessage().substring(e.getMessage().lastIndexOf(":")+1)));
        }catch (Exception e){
            throw new BentenJiraException(e.getMessage());
        }

        return bentenHandlerResponse;
    }
}
