package com.intuit.benten.splunk.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import org.springframework.stereotype.Component;

@Component
@ActionHandler(action = SplunkActions.ACTION_SPLUNK_HELP)
public class SplunkHelpActionHandler implements BentenActionHandler {
    @Override
    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String helpItem = BentenMessageHelper.getParameterAsString(bentenMessage, SplunkActionParameters.PARAMETER_SPLUNK_HELP);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
        bentenSlackResponse.setSlackText(helpItem);
        bentenHandlerResponse.setBentenSlackResponse(bentenSlackResponse);

        return bentenHandlerResponse;
    }
}