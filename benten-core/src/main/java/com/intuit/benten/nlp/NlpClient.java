package com.intuit.benten.nlp;

import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.hackernews.exceptions.AiException;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public interface NlpClient {
    BentenMessage sendText(String text, String sessionId) throws AiException;
    BentenMessage sendText(String text, String sessionId, boolean reset) throws AiException;
    boolean isActionable(String action);
}
