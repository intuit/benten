package com.intuit.benten.common.nlp;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.protobuf.Value;
import com.intuit.benten.common.channel.Channel;
import com.intuit.benten.common.channel.ChannelInformation;
import com.intuit.benten.common.constants.SlackConstants;
import com.intuit.benten.common.helpers.BentenMessageHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class BentenMessage {

    private String action;
    private boolean isActionComplete;
    private HashMap<String, JsonElement> parameters;
    private String speech;
    private Channel channel;
    private ChannelInformation channelInformation;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isActionComplete() {
        return isActionComplete;
    }

    public void setIsActionComplete(boolean isActionComplete) {
        this.isActionComplete = isActionComplete;
    }

    public HashMap<String, JsonElement> getParameters() {
        if (null == parameters) {
            parameters = new HashMap<>();
        }
        return parameters;
    }

    public void setParameters(HashMap<String, JsonElement> parameters) {
        this.parameters = parameters;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ChannelInformation getChannelInformation() {
        return channelInformation;
    }

    public void setChannelInformation(ChannelInformation channelInformation) {
        this.channelInformation = channelInformation;
    }

    public String toString(){
        return "action="+action+", user="+ BentenMessageHelper.getParameterAsString(parameters,SlackConstants.CURRENT_USER);
    }

    public void setParameterstoJsonElement(Set<Map.Entry<String,com.google.protobuf.Value>> entrySet) {
            for (Map.Entry<String, Value> entry : entrySet) {
                if (entry.getValue().getKindCase().getNumber() == Value.STRING_VALUE_FIELD_NUMBER) {
                    this.parameters.put(entry.getKey(), new JsonPrimitive(entry.getValue().getStringValue()));
                } else if (entry.getValue().getKindCase().getNumber() == Value.STRUCT_VALUE_FIELD_NUMBER) {
                    this.parameters.put(entry.getKey(), new JsonPrimitive(String.valueOf(entry.getValue().getStructValue())));
                }
                else if (entry.getValue().getKindCase().getNumber() == Value.NUMBER_VALUE_FIELD_NUMBER) {
                    this.parameters.put(entry.getKey(), new JsonPrimitive(String.valueOf(entry.getValue().getNumberValue())));
                }
            }
    }
}
