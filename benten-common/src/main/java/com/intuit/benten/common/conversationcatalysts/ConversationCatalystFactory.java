package com.intuit.benten.common.conversationcatalysts;

import java.util.HashMap;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public interface ConversationCatalystFactory {

    HashMap<String,ConversationCatalyst> getConversationCatalystMap();
}
