package com.intuit.benten.common.actionhandlers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class BentenSlackResponse {

    private String slackText;
    private List<BentenSlackAttachment> bentenSlackAttachments = new ArrayList<>();


    public List<BentenSlackAttachment> getBentenSlackAttachments() {
        return bentenSlackAttachments;
    }

    public void setBentenSlackAttachments(List<BentenSlackAttachment> bentenSlackAttachments) {
        this.bentenSlackAttachments = bentenSlackAttachments;
    }

    public String getSlackText() {
        return slackText;
    }

    public void setSlackText(String slackText) {
        this.slackText = slackText;
    }

    public void addBentenSlackAttachment(BentenSlackAttachment bentenSlackAttachment){
        this.bentenSlackAttachments.add(bentenSlackAttachment);
    }

}
