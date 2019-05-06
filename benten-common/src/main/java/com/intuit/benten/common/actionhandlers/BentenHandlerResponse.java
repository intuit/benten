package com.intuit.benten.common.actionhandlers;

import org.springframework.stereotype.Component;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class BentenHandlerResponse {

    private String slackText;
    private BentenSlackResponse bentenSlackResponse;
    private BentenHtmlResponse bentenHtmlResponse;

    public String getSlackText() {
        return slackText;
    }

    public void setSlackText(String slackText) {
        this.slackText = slackText;
    }

    public BentenSlackResponse getBentenSlackResponse() {
        return bentenSlackResponse;
    }

    public void setBentenSlackResponse(BentenSlackResponse bentenSlackResponse) {
        this.bentenSlackResponse = bentenSlackResponse;
    }

    public BentenHtmlResponse getBentenHtmlResponse() {
        return bentenHtmlResponse;
    }

    public void setBentenHtmlResponse(BentenHtmlResponse bentenHtmlResponse) {
        this.bentenHtmlResponse = bentenHtmlResponse;
    }
}
