package com.intuit.benten.jira.utils;

import com.intuit.benten.common.actionhandlers.BentenSlackAttachment;
import com.intuit.benten.common.actionhandlers.BentenSlackField;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.jira.http.JiraHttpHelper;
import com.intuit.benten.jira.model.Field;
import com.intuit.benten.jira.model.Issue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class SlackMessageRenderer {
    public static String renderIssueList(List<Issue> issues){
        SlackFormatter slackFormatter = SlackFormatter.create();
        if(issues.isEmpty()){
            slackFormatter.text("There are no issues assigned to this user currently.");
        }
        for(Issue issue:issues){
            slackFormatter.link(issue.getUrl(),issue.getKey())
                    .code(issue.getStatus().getName())
                    .text(issue.getSummary())
                    .code(issue.getPriority().getName())
                    .newline();
        }

        return slackFormatter.build();
    }

    public static BentenSlackResponse renderIssueDetails(Issue issue) {

        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        List<BentenSlackField> bentenSlackFields = new ArrayList<BentenSlackField>();

        List<BentenSlackAttachment> bentenSlackAttachments = new ArrayList<BentenSlackAttachment>();
        BentenSlackAttachment bentenSlackAttachment = new BentenSlackAttachment();
        bentenSlackAttachment.setText("Below are the issue details");
        SlackFormatter slackFormatter = SlackFormatter.create();
        slackFormatter.link(issue.getUrl(),issue.getKey()).text(issue.getSummary()).newline().newline();

        bentenSlackFields.add(new BentenSlackField(Field.ASSIGNEE.toUpperCase(),issue.getAssignee().getDisplayName(),true));
        bentenSlackFields.add(new BentenSlackField(Field.PRIORITY.toUpperCase(),issue.getPriority().getName(),true));
        bentenSlackFields.add(new BentenSlackField(Field.STATUS.toUpperCase(),issue.getStatus().getName(),true));
        bentenSlackFields.add(new BentenSlackField(Field.ISSUE_TYPE.toUpperCase(),issue.getIssueType().getName(),true));
        bentenSlackFields.add(new BentenSlackField(Field.DESCRIPTION.toUpperCase(),issue.getDescription(),false));

        bentenSlackAttachment.setText(slackFormatter.build());
        bentenSlackAttachment.setBentenSlackFields(bentenSlackFields);
        bentenSlackAttachments.add(bentenSlackAttachment);

        bentenSlackResponse.setBentenSlackAttachments(bentenSlackAttachments);

        return bentenSlackResponse;

    }

    public static BentenSlackResponse logWorkMessage(String issueKey,String time) {

        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();
        bentenSlackResponse.setSlackText(slackFormatter
                .text("Done! Logged ")
                .code(time)
                .text(" against "+issueKey)
                .build());

        return bentenSlackResponse;

    }

    public static BentenSlackResponse possibleTransitionsMessage(String issueKey,String status, List<String> statuses) {

        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();
        SlackFormatter slackFormatterStatusTransitions = SlackFormatter.create();

        for(String toStatus : statuses){
            slackFormatterStatusTransitions.code(toStatus);
        }
        bentenSlackResponse.setSlackText(slackFormatter
                .text("Issue ")
                .code(issueKey)
                .text(" cannot be moved to ").code(status)
                .newline()
                .bold("Possible transition states: ")
                .newline()
                .text(slackFormatterStatusTransitions.build())
                .build());

        return bentenSlackResponse;

    }

    public static BentenSlackResponse transitionMessage(String issueKey,String status) {

        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();

        bentenSlackResponse.setSlackText(slackFormatter
                .text("Done! Issue ")
                .code(issueKey)
                .text(" was moved to ")
                .bold(status)
                .build());

        return bentenSlackResponse;

    }

    public static BentenSlackResponse commentMessage(String issueKey)  {

        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();
        try {
            bentenSlackResponse.setSlackText(slackFormatter
                    .text("You commented on ")
                    .link(JiraHttpHelper.browseIssueUri(issueKey).toString(), issueKey)
                    .build());
        }catch (Exception e){
            throw new BentenJiraException(e.getMessage());
        }

        return bentenSlackResponse;

    }

    public static BentenSlackResponse createIssueMessage(String issueUri) {

        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();

        bentenSlackResponse.setSlackText(slackFormatter.link(issueUri)
                .build());

        return bentenSlackResponse;

    }

    public static BentenSlackResponse errorMessage(String errorMessage) {

        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();

        bentenSlackResponse.setSlackText(slackFormatter
                .text("An error occured when I tried to do this operation:")
                .newline()
                .code(errorMessage)
                .build());

        return bentenSlackResponse;

    }


}
