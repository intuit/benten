package com.intuit.benten.channel.slack;


import com.intuit.benten.common.nlp.BentenMessage;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */

public class SlackHelperPayload {

    private BentenMessage bentenMessage;
    private String channel;

    public void setNlpBentenMessage(BentenMessage bentenMessage) {
        this.bentenMessage = bentenMessage;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    private SlackUser currentUser;
    private boolean repeateQuestion = false;

    public SlackHelperPayload(BentenMessage bentenMessage, String channel) {
        this.bentenMessage = bentenMessage;
        this.channel = channel;
    }

    public SlackHelperPayload(BentenMessage bentenMessage, SlackUser slackUser, String channel) {
        this.bentenMessage = bentenMessage;
        this.channel = channel;
        this.currentUser = slackUser;
    }

    public SlackHelperPayload(BentenMessage bentenMessage, SlackUser slackUser, String channel, boolean repeateQuestion) {
        this.bentenMessage = bentenMessage;
        this.channel = channel;
        this.currentUser = slackUser;
        this.repeateQuestion=repeateQuestion;
    }

    public BentenMessage getNlpBentenMessage() {
        return bentenMessage;
    }

    public String getChannel() {
        return channel;
    }

    public SlackUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(SlackUser currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isRepeateQuestion() {
        return repeateQuestion;
    }

    public void setRepeateQuestion(boolean repeateQuestion) {
        this.repeateQuestion = repeateQuestion;
    }

}

