package com.intuit.benten.renderers;

import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.formatters.SlackFormatter;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class SlackMessageRenderer {

    public static BentenSlackResponse errorMessage(String errorMessage) {

        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();

        bentenSlackResponse.setSlackText(slackFormatter
                .text("An error occured when I tried to do this operation:")
                .newline()
                .code(errorMessage)
                .build());

        return bentenSlackResponse;

    }
}
