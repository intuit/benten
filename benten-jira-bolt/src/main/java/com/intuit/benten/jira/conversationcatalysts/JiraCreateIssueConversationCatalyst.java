package com.intuit.benten.jira.conversationcatalysts;

import com.intuit.benten.common.annotations.BentenConversationCatalyst;
import com.intuit.benten.common.bentennlp.ConversationFragment;
import com.intuit.benten.common.bentennlp.Question;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.common.bentennlp.Conversation;
import com.intuit.benten.common.constants.SlackConstants;
import com.intuit.benten.common.conversationcatalysts.ConversationCatalyst;
import com.intuit.benten.jira.actionhandlers.JiraActions;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@BentenConversationCatalyst(action = JiraActions.ACTION_JIRA_CREATE_ISSUE)
public class JiraCreateIssueConversationCatalyst implements ConversationCatalyst {

    @Autowired
    private BentenJiraClient bentenJiraClient;

    @Override
    public Conversation getConversation(BentenMessage bentenMessage) {
        String projectKey  = BentenMessageHelper.getParameterAsString(bentenMessage, SlackConstants.PROJECT_KEY);
        String issueType  = BentenMessageHelper.getParameterAsString(bentenMessage,SlackConstants.ISSUE_TYPE);

        JSONObject fields = bentenJiraClient.getCreateMetadata(projectKey,issueType);
        Conversation conversation;

        List<JSONObject> requiredFields = JiraConverter.getRequiredFields(fields);

        conversation = createConversation(requiredFields);

        return conversation;
    }

    public Conversation createConversation(List<JSONObject> requiredFields){

        Conversation conversation = new Conversation();
        List<ConversationFragment> conversationFragments =
                Collections.synchronizedList(new ArrayList<ConversationFragment>());

        requiredFields.parallelStream().forEach(requiredField -> {

            ConversationFragment conversationFragment = new ConversationFragment();

            Question question = new Question();
            question.setValue(requiredField.getString("name")+" ?");

            conversationFragment.setQuestion(question);

            Map<Integer,String> allowedValues= new HashMap<Integer,String>();

            if(requiredField.containsKey("allowedValues")) {

                JSONArray jsonArray = requiredField.getJSONArray("allowedValues");

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject allowedValue = jsonArray.getJSONObject(i);
                    String value;
                    if(allowedValue.containsKey("name"))
                        value = allowedValue.getString("name");
                    else
                        value = allowedValue.getString("value");

                    allowedValues.put(i, value);
                }

                conversationFragment.setAllowedValues(allowedValues);
            }
            conversationFragments.add(conversationFragment);

        });

        conversation.setConversationFragments(conversationFragments);

        return conversation;
    }
}
