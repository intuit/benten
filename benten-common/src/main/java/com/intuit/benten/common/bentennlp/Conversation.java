package com.intuit.benten.common.bentennlp;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Conversation {

    private List<ConversationFragment> conversationFragments;
    private boolean conversationComplete;

    public boolean isConversationComplete(){
        if(conversationFragments!=null){
            for (ConversationFragment conversationFragment:conversationFragments) {
                if(conversationFragment.getAnswer()==null){
                    conversationComplete=false;
                    return conversationComplete;
                }
            }
        }
        return true;
    }

    public Question nextQuestion(){
        if(conversationFragments!=null){
            for (ConversationFragment conversationFragment:conversationFragments) {
                if(conversationFragment.getAnswer()==null){
                    return conversationFragment.getQuestion();
                }
            }
        }
        return null;
    }

    public ConversationFragment nextConversationFragment(){
        if(conversationFragments!=null){
            for (ConversationFragment conversationFragment:conversationFragments) {
                if(conversationFragment.getAnswer()==null){
                    return conversationFragment;
                }
            }
        }
        return null;
    }

    public void setAnswerToActiveQuestion(Answer answer){
        if(conversationFragments!=null){
            for (ConversationFragment conversationFragment:conversationFragments) {
                if(conversationFragment.getAnswer()==null){
                    conversationFragment.setAnswer(answer);
                    return;
                }
            }
        }
    }

    public List<ConversationFragment> getConversationFragments() {
        return conversationFragments;
    }

    public void setConversationFragments(List<ConversationFragment> conversationFragments) {
        this.conversationFragments = conversationFragments;
    }

    public HashMap<String, JsonElement> getParameters(){
        HashMap<String,JsonElement> parameters=null;

        if(conversationFragments!=null){
            parameters= new HashMap<>();
            for (ConversationFragment conversationFragment:conversationFragments) {
                if(conversationFragment.getParameter()!=null){
                    JsonElement value = new JsonPrimitive(conversationFragment.getParameter().getValue());
                    parameters.put(conversationFragment.getParameter().getName(),value);
                }
            }
        }

        return parameters;
    }
}
