package com.intuit.benten.jira.actionhandlers;

import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.constants.SlackConstants;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.jira.model.Field;
import com.intuit.benten.jira.model.User;
import com.intuit.benten.jira.utils.SlackMessageRenderer;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ActionHandler(action = JiraActions.ACTION_JIRA_CREATE_ISSUE)
public class JiraCreateIssueActionHandler implements BentenActionHandler {


    @Autowired
    private BentenJiraClient bentenJiraClient;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {

        String userOfInterest = BentenMessageHelper.getParameterAsString(bentenMessage, SlackConstants.USER_OF_INTEREST);
        String currentUser = BentenMessageHelper.getParameterAsString(bentenMessage, SlackConstants.CURRENT_USER);
        String projectKey = BentenMessageHelper.getParameterAsString(bentenMessage, SlackConstants.PROJECT_KEY);
        String summary = BentenMessageHelper.getParameterAsString(bentenMessage, SlackConstants.SUMMARY);
        String issueType = BentenMessageHelper.getParameterAsString(bentenMessage, SlackConstants.ISSUE_TYPE);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();

        try {
            JSONObject metaData = bentenJiraClient.getCreateMetadata(projectKey, issueType);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Field.SUMMARY, JiraConverter.toJson(Field.SUMMARY, summary, metaData));
            jsonObject.put(Field.PROJECT, JiraConverter.toJson(Field.PROJECT, projectKey, metaData));
            jsonObject.put(Field.ISSUE_TYPE, JiraConverter.toJson(Field.ISSUE_TYPE, issueType, metaData));

            List<JSONObject> requiredFields = JiraConverter.getRequiredFields(metaData);

            requiredFields.parallelStream().forEach(requiredField -> {
                String parameter = BentenMessageHelper.getParameterAsString(bentenMessage, requiredField.getString("name"));

                if (parameter != null)
                    jsonObject.put(requiredField.get("key"), JiraConverter.toJson(requiredField.getString("key"), formParameterPayload(requiredField, parameter), metaData));
            });

            String issueUri = bentenJiraClient.createIssue(jsonObject);

            User assignee = new User();
            User reporter = new User();

            assignee.setName(currentUser);
            reporter.setName(currentUser);

            bentenJiraClient.updateAssigneeAndReporter(issueUri.substring(issueUri.lastIndexOf("/") + 1), assignee, reporter);

            bentenHandlerResponse.setBentenSlackResponse(SlackMessageRenderer.createIssueMessage(issueUri));

        }catch(BentenJiraException e){
            bentenHandlerResponse
                    .setBentenSlackResponse(SlackMessageRenderer.errorMessage(e.getMessage().substring(e.getMessage().lastIndexOf(":")+1)));
        }catch (Exception e){
            throw new BentenJiraException(e.getMessage());
        }

        return bentenHandlerResponse;
    }

    private Object formParameterPayload(JSONObject field, String value) {
        String type = field.getJSONObject("schema").getString("type");

        if (type.equals("array") || type.equals("securitylevel") || type.equals("option") || type.equals("option-with-child")) {
            return new ArrayList() {{
                add(value);
            }};
        }

        if (type.equals("number")) {
            return Integer.parseInt(value);
        } else {
            return value;
        }

    }
}
