package com.intuit.benten.jira.utils;

import com.intuit.benten.common.actionhandlers.BentenSlackAttachment;
import com.intuit.benten.common.actionhandlers.BentenSlackField;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.formatters.SlackFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class SlackHelpRenderer {

    public static BentenSlackResponse renderHelpItem(String helpItem){
        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        switch(helpItem){
            case HelpItems.CREATE_ISSUE_HELP: {
                bentenSlackResponse.getBentenSlackAttachments().add(renderCreateIssueHelp());
                break;
            }
            case HelpItems.ASSIGN_ISSUE_HELP: {
                bentenSlackResponse.getBentenSlackAttachments().add(renderAssignIssueHelp());
                break;
            }
            case HelpItems.ISSUE_DETAILS_HELP:{
                bentenSlackResponse.getBentenSlackAttachments().add(renderIssueDetailsHelp());
                break;
            }
            case HelpItems.COMMENT_ISSUE_HELP : {
                bentenSlackResponse.getBentenSlackAttachments().add(renderCommentIssueHelp());
                break;
            }
            case HelpItems.LOG_WORK_HELP : {
                bentenSlackResponse.getBentenSlackAttachments().add(renderLogWorkHelp());
                break;
            }
            case HelpItems.SEARCH_ISSUES_HELP : {
                bentenSlackResponse.getBentenSlackAttachments().add(renderSearchIssuesHelp());
                break;
            }
            case HelpItems.TRANSITION_ISSUE_HELP: {
                bentenSlackResponse.getBentenSlackAttachments().add(renderTransitionIssueHelp());
                break;
            }
            case HelpItems.SPRINT_VELOCITY_HELP: {
                bentenSlackResponse.getBentenSlackAttachments().add(renderSprintVelocityHelp());
                break;
            }
            case HelpItems.CYCLE_TIME_HELP: {
                bentenSlackResponse.getBentenSlackAttachments().add(renderSprintVelocityHelp());
                break;
            }
            case HelpItems.JIRA_HELP: {
                bentenSlackResponse.getBentenSlackAttachments().add(renderHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderSearchIssuesHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderCreateIssueHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderIssueDetailsHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderTransitionIssueHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderAssignIssueHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderCommentIssueHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderLogWorkHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderSprintVelocityHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderCycleTimeHelp());

            }
        }

        return bentenSlackResponse;
    }


    public static BentenSlackAttachment renderHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Help",
                "Get help related to specific jira item or all of them");

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("help jira")
                .code("help jira create issue").code("help jira assign issue ").code("...")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderCreateIssueHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Create jira issue",
                "Create any type of issue in Jira. I will prompt you if there is any required field missing.");

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("create jira story ")
                .code("create jira bug ").code("create jira subtask ")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("create jira story Experience benten jira integration project BENTEN")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderAssignIssueHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Assign issue",
                "Assign issue to a user (select from the type ahead). ");

        BentenSlackField format = new BentenSlackField("Format: ", SlackFormatter.create().text("assign issue")
                .code("<ISSUE KEY>")
                .text(" to ")
                .code("<slack user>")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(format);

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("assign issue ")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("assign issue BENTEN-1 to @Divakar")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderIssueDetailsHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Issue details",
                "Get the details of a specific issue.");

        BentenSlackField format = new BentenSlackField("Format: ", SlackFormatter.create().text("details of issue ")
                .code("<ISSUE KEY>")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(format);

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("details of issue ")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("details of issue BENTEN-1")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderCommentIssueHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Comment",
                "Add comment to a issue.");

        BentenSlackField format = new BentenSlackField("Format: ", SlackFormatter.create().text("comment on issue")
                .code("<ISSUE KEY>")
                .code("<comment>")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(format);

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("comment on issue ")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("comment on issue BENTEN-1 this is an example comment")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderSearchIssuesHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("List issues",
                "Get issues of a specific user.");

        BentenSlackField format = new BentenSlackField("Format: ", SlackFormatter.create().text("my issues ").newline()
                .text("issues of ").code("<slack user>")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(format);

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("my issues")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("issues of @Divakar").text("")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderLogWorkHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Log Work",
                "Log work against a specific issue.");

        BentenSlackField format = new BentenSlackField("Format: ", SlackFormatter.create().text("log ").code("<time> ").text(" against issue ")
                .code(" <ISSUE KEY>").newline().text("log ").code("git reset <time> ").text(" against issue ")
                .code(" <ISSUE KEY>").code("<comment>")
                .build(),false );

        bentenSlackAttachment.addBentenSlackField(format);

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("log ")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("log 3h 4m against issue BENTEN-1 this is a comment for logging work").text("")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderTransitionIssueHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Transition",
                "Transiton issue to another state.");

        BentenSlackField format = new BentenSlackField("Format: ", SlackFormatter.create().text("move ")
                .code(" <ISSUE KEY> ").text("to ").code("<STATE>")
                .build(),false );

        bentenSlackAttachment.addBentenSlackField(format);

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("move ")
                .code("transition")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("move BENTEN-1 to inprogress").text("")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderSprintVelocityHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Velocity",
                "Sprint velocity of a board for specified number of sprints.");

        BentenSlackField format = new BentenSlackField("Format: ", SlackFormatter.create().text("velocity of board")
                .code(" <BOARD NAME> ").text("over last").code(" <NO OF SPRINTS> ").text("sprints")
                .build(),false );

        bentenSlackAttachment.addBentenSlackField(format);

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("Velocity")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("velocity of board Benten over last 2 sprints").text("")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderCycleTimeHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Cycle Time",
                "Cycle time of a board for specified number of sprints.");

        BentenSlackField format = new BentenSlackField("Format: ", SlackFormatter.create().text("cycle time of board")
                .code(" <BOARD NAME> ").text("over last").code(" <NO OF SPRINTS> ").text("sprints")
                .build(),false );

        bentenSlackAttachment.addBentenSlackField(format);

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("cycle time")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("cycle time of board Benten over last 2 sprints").text("")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    private static BentenSlackAttachment createBentenSlackAttachment(String title, String text) {
        BentenSlackAttachment bentenSlackAttachment = new BentenSlackAttachment();
        bentenSlackAttachment.setTitle(title);
        bentenSlackAttachment.setText(text);
        return bentenSlackAttachment;
    }
}
