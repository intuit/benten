package com.intuit.benten.jira.actionhandlers;

import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.constants.SlackConstants;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.jira.model.Field;
import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.utils.SlackMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ActionHandler(action = JiraActions.ACTION_JIRA_SEARCH_ISSUES)
public class JiraSearchIssuesByUserActionHandler implements BentenActionHandler {


   @Autowired
   private BentenJiraClient bentenJiraClient;

   public BentenHandlerResponse handle(BentenMessage bentenMessage) {

      String userOfInterest= BentenMessageHelper.getParameterAsString(bentenMessage,SlackConstants.USER_OF_INTEREST);
      String currentUser = BentenMessageHelper.getParameterAsString(bentenMessage,SlackConstants.CURRENT_USER);
      int noOfIssues = 10;

      String assignee;
      if(userOfInterest!=null){
         assignee=userOfInterest;
      } else{
         assignee=currentUser;
      }

      String expandedFields = Field.ASSIGNEE
              .concat(","+Field.STATUS)
              .concat(","+Field.PRIORITY)
              .concat(","+Field.ISSUE_TYPE)
              .concat(","+Field.PRIORITY)
              .concat(","+Field.SUMMARY);

      //noOfIssues = parameters.containsKey("noOfIssues") ? noOfIssues = parameters.get("noOfIssues").getAsInt() : 10;

      BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
      try {

         List<Issue> issues =
                 bentenJiraClient.searchIssuesByAssignee(assignee, expandedFields, noOfIssues);
         BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
         bentenSlackResponse.setSlackText(SlackMessageRenderer.renderIssueList(issues));
         bentenHandlerResponse.setBentenSlackResponse(bentenSlackResponse);
      }catch (BentenJiraException e) {
         bentenHandlerResponse
                 .setBentenSlackResponse(SlackMessageRenderer.errorMessage(e.getMessage().substring(e.getMessage().lastIndexOf(":") + 1)));
      } catch (Exception e) {
         throw new BentenJiraException(e.getMessage());
      }

      return bentenHandlerResponse;
   }
}
