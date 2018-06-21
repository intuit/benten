package com.intuit.benten.jira.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.utils.HelpItems;
import com.intuit.benten.jira.utils.SlackHelpRenderer;
import org.springframework.stereotype.Component;


/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ActionHandler(action = JiraActions.ACTION_JIRA_HELP)
public class JiraHelpActionHandler implements BentenActionHandler{


    @Override
    public BentenHandlerResponse handle(BentenMessage bentenMessage) {

        String helpItem = BentenMessageHelper.getParameterAsString(bentenMessage, JiraActionParameters.PARAMETER_HELP_ITEM);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        BentenSlackResponse bentenSlackResponse = SlackHelpRenderer.renderHelpItem(helpItem);
        bentenHandlerResponse.setBentenSlackResponse(bentenSlackResponse);

        return bentenHandlerResponse;
    }
}
