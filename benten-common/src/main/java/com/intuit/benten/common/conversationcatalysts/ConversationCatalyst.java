package com.intuit.benten.common.conversationcatalysts;

import com.intuit.benten.common.bentennlp.Conversation;
import com.intuit.benten.common.nlp.BentenMessage;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public interface ConversationCatalyst {
    Conversation getConversation(BentenMessage bentenMessage);
}
