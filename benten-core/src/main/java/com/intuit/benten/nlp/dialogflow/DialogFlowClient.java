package com.intuit.benten.nlp.dialogflow;

import com.google.cloud.dialogflow.v2.*;
import com.google.cloud.dialogflow.v2.TextInput.Builder;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.nlp.NlpClient;
import com.intuit.benten.properties.AiProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import com.intuit.benten.exceptions.AiException;
import java.util.HashMap;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */

public class DialogFlowClient implements NlpClient {

    private String PROJECT_ID;

    public DialogFlowClient(String PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    @Override
    public BentenMessage sendText(String text, String sessionId) throws AiException{
        return sendText(text,sessionId,false);
    }

    @Override
    public BentenMessage sendText(String text, String sessionId, boolean reset) throws AiException{

        SessionsClient sessionsClient = null;
        try {
            sessionsClient = SessionsClient.create();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SessionName session = SessionName.of(PROJECT_ID, sessionId);
            BentenMessage bentenMessage;

            Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode("en-US");
            // Build the query with the TextInput
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            DetectIntentRequest detectIntentRequest =
                    DetectIntentRequest.newBuilder()
                        .setSession(session.toString())
                        .setQueryInput(queryInput)
                        .build();
            DetectIntentResponse response = sessionsClient.detectIntent(detectIntentRequest);
            // Display the query result
            QueryResult queryResult = response.getQueryResult();

            bentenMessage = new BentenMessage();
            bentenMessage.setSpeech(queryResult.getFulfillmentText());
            bentenMessage.setAction(queryResult.getAction());
            bentenMessage.setIsActionComplete(queryResult.getAllRequiredParamsPresent());
            bentenMessage.setParameters(new HashMap<>());
            //transform v2 Dialogflow params to JsonElement
            bentenMessage.setParameterstoJsonElement(queryResult.getParameters().getFieldsMap().entrySet());
        return bentenMessage;
    }

    @Override
    public boolean isActionable(String action) {
        if(action.contains(DialogFlowActions.ACTION_HELP)
                || action.contains(DialogFlowActions.ACTION_INPUT_UNKOWN)
                || action.contains(DialogFlowActions.ACTION_SMALL_TALK)
                || action.contains(DialogFlowActions.ACTION_WELCOME)){
            return false;
        }
        return true;
    }
}
