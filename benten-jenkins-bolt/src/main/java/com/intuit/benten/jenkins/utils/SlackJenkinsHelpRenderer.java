package com.intuit.benten.jenkins.utils;

import com.intuit.benten.common.actionhandlers.BentenSlackAttachment;
import com.intuit.benten.common.actionhandlers.BentenSlackField;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.formatters.SlackFormatter;

/**
 * Created by sshashidhar on 20/06/18.
 */
public class SlackJenkinsHelpRenderer {

    public static BentenSlackResponse renderHelpItem(String helpItem){
        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        switch(helpItem){
            case HelpItems.JENKINS_JOB_DETAILS: {
                bentenSlackResponse.getBentenSlackAttachments().add(renderJenkinsJobDetailsHelp());
                break;
            }
            case HelpItems.JENKINS_SEARCH_JOB: {
                bentenSlackResponse.getBentenSlackAttachments().add(renderJenkinsSearchJobHelp());
                break;
            }
            case HelpItems.JENKINS_BUILD_JOB:{
                bentenSlackResponse.getBentenSlackAttachments().add(renderJenkinsBuildJobHelp());
                break;
            }
            case HelpItems.JENKINS_HELP: {
                bentenSlackResponse.getBentenSlackAttachments().add(renderHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderJenkinsJobDetailsHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderJenkinsSearchJobHelp());
                bentenSlackResponse.getBentenSlackAttachments().add(renderJenkinsBuildJobHelp());
            }
        }
        return bentenSlackResponse;
    }


    public static BentenSlackAttachment renderHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Help",
                "Get help related to specific jenkins item or all of them");

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("help jenkins")
                .code("...")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderJenkinsJobDetailsHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Jenkins Job Details",
                "This will fetch the details of a job from Jenkins. It will show the status and last build details.");

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("details of jenkins job ")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("details of jenkins job Benten-Env-Stability")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderJenkinsSearchJobHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Jenkins Job Search",
                "This will search for jobs in your jenkins instance, this will search by prefix. ");

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("search for jenkins job ")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("search for jenkins job with prefix Benten")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(shorthand);

        return bentenSlackAttachment;
    }

    public static BentenSlackAttachment renderJenkinsBuildJobHelp(){

        BentenSlackAttachment bentenSlackAttachment = createBentenSlackAttachment("Jenkins Build Job",
                "This will build a specifc job in jenkins and show a build number. ");

        BentenSlackField start = new BentenSlackField("Simple Start: ", SlackFormatter.create().code("build jenkins job ")
                .build(),false );
        bentenSlackAttachment.addBentenSlackField(start);

        BentenSlackField shorthand = new BentenSlackField("Example: ", SlackFormatter.create()
                .preformatted("build jenkins job Benten-Env-Stability")
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
