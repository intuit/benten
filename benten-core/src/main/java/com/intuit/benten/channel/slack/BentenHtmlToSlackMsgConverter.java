package com.intuit.benten.channel.slack;

import allbegray.slack.type.Attachment;
import allbegray.slack.webapi.method.chats.ChatPostMessageMethod;
import com.intuit.benten.common.actionhandlers.BentenHtmlResponse;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class BentenHtmlToSlackMsgConverter {

    public static ChatPostMessageMethod transform(BentenHtmlResponse bentenHtmlResponse, String text, String channel) {
        ChatPostMessageMethod chatPostMessageMethod = new ChatPostMessageMethod(channel, text);
        Attachment attachment = new Attachment();

        return chatPostMessageMethod;
    }

}
