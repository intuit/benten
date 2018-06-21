package com.intuit.benten.jira.actionhandlers;

import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.constants.SlackConstants;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.jira.utils.SlackMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ActionHandler(action = JiraActions.ACTION_JIRA_LOG_WORK)
public class JiraLogWorkActionHandler implements BentenActionHandler {

    @Autowired
    private BentenJiraClient bentenJiraClient;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String issueKey = BentenMessageHelper.getParameterAsString(bentenMessage, JiraActionParameters.PARAMETER_ISSUE_KEY);
        String time = BentenMessageHelper.getParameterAsString(bentenMessage, JiraActionParameters.PARAMETER_TIME);
        String currentUser = BentenMessageHelper.getParameterAsString(bentenMessage, SlackConstants.CURRENT_USER);
        String comment = BentenMessageHelper.getParameterAsString(bentenMessage, SlackConstants.COMMENT);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();

        try {

            bentenJiraClient.logWork(issueKey, time, currentUser, comment);
            bentenHandlerResponse
                    .setBentenSlackResponse(SlackMessageRenderer.logWorkMessage(issueKey, time));
        } catch (BentenJiraException e) {
            bentenHandlerResponse
                    .setBentenSlackResponse(SlackMessageRenderer.errorMessage(e.getMessage().substring(e.getMessage().lastIndexOf(":") + 1)));
        } catch (Exception e) {
            throw new BentenJiraException(e.getMessage());
        }

        return bentenHandlerResponse;
    }
}
