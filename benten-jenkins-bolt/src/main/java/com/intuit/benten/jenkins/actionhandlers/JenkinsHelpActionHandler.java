package com.intuit.benten.jenkins.actionhandlers;

/**
 * Created by sshashidhar on 20/06/18.
 */

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jenkins.utils.SlackJenkinsHelpRenderer;
import org.springframework.stereotype.Component;

@Component
@ActionHandler(action = JenkinsActions.ACTION_JENKINS_HELP)
public class JenkinsHelpActionHandler implements BentenActionHandler {
    @Override
    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String helpItem = BentenMessageHelper.getParameterAsString(bentenMessage, JenkinsActionParameters.PARAMETER_HELP_ITEM);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        BentenSlackResponse bentenSlackResponse = SlackJenkinsHelpRenderer.renderHelpItem(helpItem);
        bentenHandlerResponse.setBentenSlackResponse(bentenSlackResponse);

        return bentenHandlerResponse;
    }
}
