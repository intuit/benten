package com.intuit.benten.common.channel;

import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public interface Channel {
    void sendMessage(BentenHandlerResponse bentenHandlerResponse, ChannelInformation channelInformation);
}
