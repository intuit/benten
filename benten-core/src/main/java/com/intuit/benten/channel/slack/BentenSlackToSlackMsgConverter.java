package com.intuit.benten.channel.slack;

import allbegray.slack.type.Attachment;
import allbegray.slack.type.Color;
import allbegray.slack.type.Field;
import allbegray.slack.webapi.method.chats.ChatPostMessageMethod;
import com.intuit.benten.common.actionhandlers.BentenSlackAttachment;
import com.intuit.benten.common.actionhandlers.BentenSlackField;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class BentenSlackToSlackMsgConverter {

    public static ChatPostMessageMethod transform(BentenSlackResponse bentenSlackResponse, String text, String channel) {

        if (text == null) {
            text = "";
        }

        ChatPostMessageMethod chatPostMessageMethod = new ChatPostMessageMethod(channel, text);
        if (bentenSlackResponse.getBentenSlackAttachments() != null && !bentenSlackResponse.getBentenSlackAttachments().isEmpty()) {
            List<BentenSlackAttachment> bentenSlackAttachments = bentenSlackResponse.getBentenSlackAttachments();
            List<Attachment> attachments = new ArrayList<Attachment>();
            for (BentenSlackAttachment bentenSlackAttachment : bentenSlackAttachments) {
                List<BentenSlackField> bentenSlackFields = bentenSlackAttachment.getBentenSlackFields();
                List<Field> fields = new ArrayList<Field>();
                if(!bentenSlackFields.isEmpty()){
                    for (BentenSlackField bentenSlackField : bentenSlackFields) {
                        Field field = new Field(bentenSlackField.getTitle(), bentenSlackField.getValue(), bentenSlackField.isShort());
                        fields.add(field);
                    }
                }

                Attachment attachment = new Attachment();
                attachment.setFields(fields);
                if (bentenSlackAttachment.getColor() != null) {
                    attachment.setColor(Color.valueOf(bentenSlackAttachment.getColor()));
                }
                else {
                    attachment.setColor(Color.GOOD);
                }
                attachments.add(attachment);

                if(bentenSlackAttachment.getTitle() != null) {
                    attachment.setTitle(bentenSlackAttachment.getTitle());
                }
                if(bentenSlackAttachment.getPretext() != null){
                    attachment.setPretext(bentenSlackAttachment.getPretext());
                }
                if(bentenSlackAttachment.getText() != null){
                    attachment.setText(bentenSlackAttachment.getText());
                }

            }
            chatPostMessageMethod.setAttachments(attachments);
        }
        return chatPostMessageMethod;
    }

}
