package com.intuit.benten.jira.actionhandlers;

import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.constants.SlackConstants;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.jira.model.Transition;
import com.intuit.benten.jira.model.User;
import com.intuit.benten.jira.utils.SlackMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ActionHandler(action = JiraActions.ACTION_JIRA_TRANSITION_ISSUE)
public class JiraTransitionIssueActionHandler implements BentenActionHandler {

    @Autowired
    private BentenJiraClient bentenJiraClient;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String issueKey = BentenMessageHelper.getParameterAsString(bentenMessage,JiraActionParameters.PARAMETER_ISSUE_KEY);
        String status = BentenMessageHelper.getParameterAsString(bentenMessage,JiraActionParameters.PARAMETER_STATUS);
        String assigneeName = BentenMessageHelper.getParameterAsString(bentenMessage, SlackConstants.CURRENT_USER);

        List<Transition> transitions = bentenJiraClient.possibleTransitions(issueKey);

        Transition toTransition = matchingTransition(status,transitions);
        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();

        if(toTransition==null){
            bentenHandlerResponse.setBentenSlackResponse(SlackMessageRenderer.possibleTransitionsMessage(issueKey,status,possibleTransitonValues(transitions)));
            return  bentenHandlerResponse;
        }

        User assignee = new User();
        assignee.setName(assigneeName);
        try {
            bentenJiraClient.transition(issueKey,toTransition);
            bentenJiraClient.possibleTransitions(issueKey);
            bentenJiraClient.assignIssueToUser(issueKey,assignee);
            bentenHandlerResponse
                        .setBentenSlackResponse(SlackMessageRenderer.transitionMessage(issueKey, status));
        }catch(BentenJiraException e){
            bentenHandlerResponse
                    .setBentenSlackResponse(SlackMessageRenderer.errorMessage(e.getMessage().substring(e.getMessage().lastIndexOf(":")+1)));
        }
        return bentenHandlerResponse;
    }

    private Transition matchingTransition(String status, List<Transition> transitions){
        for(Transition transition : transitions){
            if(status.equals(transition.getTo().getName())){
                return transition;
            }
        }
        return null;
    }

    private List<String> possibleTransitonValues( List<Transition> transitions){
       List<String> states = new ArrayList<String>();
        for(Transition transition : transitions){
           states.add(transition.getTo().getName());
        }
        return states;
    }
}
