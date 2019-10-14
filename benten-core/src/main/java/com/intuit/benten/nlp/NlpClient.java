package com.intuit.benten.nlp;

import com.intuit.benten.common.nlp.BentenMessage;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public interface NlpClient {
    BentenMessage sendText(String text, String sessionId);
    BentenMessage sendText(String text, String sessionId, boolean reset);
    boolean isActionable(String action);
}
