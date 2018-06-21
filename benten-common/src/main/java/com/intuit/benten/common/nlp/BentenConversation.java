package com.intuit.benten.common.nlp;

import com.intuit.benten.common.bentennlp.Conversation;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class BentenConversation {

    private Conversation conversation;
    private BentenMessage bentenMessage;

    public BentenConversation(Conversation conversation, BentenMessage bentenMessage){
        this.conversation = conversation;
        this.bentenMessage = bentenMessage;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public BentenMessage getNlpBentenMessage() {
        return bentenMessage;
    }

    public void setNlpBentenMessage(BentenMessage bentenMessage) {
        this.bentenMessage = bentenMessage;
    }

}
