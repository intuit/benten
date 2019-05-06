package com.intuit.benten.common.actionhandlers;

import com.intuit.benten.common.nlp.BentenMessage;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public interface BentenActionHandler {
    BentenHandlerResponse handle(BentenMessage bentenMessage);
}
