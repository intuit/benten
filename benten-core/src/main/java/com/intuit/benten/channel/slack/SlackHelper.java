package com.intuit.benten.channel.slack;

import allbegray.slack.rtm.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.common.nlp.BentenMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class SlackHelper {

    public static  ObjectMapper mapper = new ObjectMapper();

    public static SlackHelperPayload slackHelperPayload(BentenMessage bentenMessage, String channel){
        return new SlackHelperPayload(bentenMessage,channel);
    }

    public static String slackRTMPayload(SlackHelperPayload slackHelperPayload){

        ObjectNode textMessage =
                getSlackRTMMessage(slackHelperPayload.getNlpBentenMessage().getSpeech(), slackHelperPayload.getChannel());

        return textMessage.toString();

    }

    public static String slackRTMPayload(String text,String channel){

        ObjectNode textMessage = getSlackRTMMessage(text, channel);

        return textMessage.toString();
    }

    private static ObjectNode getSlackRTMMessage(String text, String channel) {
        ObjectNode textMessage = mapper.createObjectNode();
        if(text.isEmpty()){
            text="";
        }
        {
            SlackFormatter slackFormatter = SlackFormatter.create();
            String[] strings = text.split("\\\\n");
            for(int i=0;i< strings.length;i++){
                slackFormatter.text(strings[i]);
                slackFormatter.newline();
            }

            textMessage.put("text", slackFormatter.build());
        }
        textMessage.put("channel",channel);
        textMessage.put("type", Event.MESSAGE.toString().toLowerCase());
        return textMessage;
    }

    public static String extractUserFromMessage(String text){
        Pattern pattern = Pattern.compile("<@(.*?)>");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find())
        {
            return matcher.group(1);
        }
        return null;
    }
}
