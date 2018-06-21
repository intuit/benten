package com.intuit.benten.common.channel;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class ChannelInformation {

    private String channelId;

    public ChannelInformation(String channelId){
        this.channelId = channelId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
