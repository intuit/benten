package com.intuit.benten.common.actionhandlers;

import java.util.HashMap;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public interface ActionHandlerFactory {

    HashMap<String,BentenActionHandler> getActionHandlerMap();

}

