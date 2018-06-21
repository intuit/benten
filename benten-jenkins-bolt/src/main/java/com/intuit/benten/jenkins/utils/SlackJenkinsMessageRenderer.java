package com.intuit.benten.jenkins.utils;

import com.intuit.benten.common.actionhandlers.BentenSlackField;
import com.intuit.benten.jenkins.exceptions.BentenJenkinsException;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.common.actionhandlers.BentenSlackAttachment;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.JobWithDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sshashidhar on 2/24/18.
 */
public class SlackJenkinsMessageRenderer {

    public static BentenSlackResponse renderJobList(Collection<String> jobListMap) {
        BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
        if(jobListMap.size()>0){
            bentenSlackResponse.setSlackText("Found Job(s):\n " + String.join("\n", jobListMap));
        } else {
            bentenSlackResponse.setSlackText("No Jenkins Jobs found for the prefix");
        }

        return bentenSlackResponse;
    }

    public static BentenSlackResponse renderJobDetailByJobName(JobWithDetails jobWithDetails) {

        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        try{
            if(jobWithDetails!=null){
                List<BentenSlackField> bentenSlackFields = new ArrayList<BentenSlackField>();
                List<BentenSlackAttachment> bentenSlackAttachments = new ArrayList<BentenSlackAttachment>();
                BentenSlackAttachment bentenSlackAttachment = new BentenSlackAttachment();

                bentenSlackResponse.setSlackText("Below is the Job details");
                SlackFormatter slackFormatter = SlackFormatter.create();
                slackFormatter.link(jobWithDetails.getUrl(),jobWithDetails.getDisplayName()).text(jobWithDetails.getDescription()).newline().newline();

                BuildResult buildResult = jobWithDetails.getLastBuild().details().getResult();
                if(buildResult!=null) {
                    bentenSlackFields.add(new BentenSlackField("Last Build Status", buildResult.toString(), true));
                    if(buildResult.equals(BuildResult.SUCCESS))
                        bentenSlackAttachment.setColor("GOOD");
                    else if(buildResult.equals(BuildResult.FAILURE))
                        bentenSlackAttachment.setColor("DANGER");
                    else if(buildResult.equals(BuildResult.UNSTABLE))
                        bentenSlackAttachment.setColor("WARNING");
                    else
                        bentenSlackAttachment.setColor("WARNING");
                }
                else {
                    bentenSlackFields.add(new BentenSlackField("Last Build Status", "INPROGRESS", true));
                }
                bentenSlackFields.add(new BentenSlackField("Last Build",Integer.toString(jobWithDetails.getLastBuild().getNumber()),true));
                bentenSlackFields.add(new BentenSlackField("Last Successful Build",Integer.toString(jobWithDetails.getLastSuccessfulBuild().getNumber()),true));
                bentenSlackFields.add(new BentenSlackField("Last Failed Build",Integer.toString(jobWithDetails.getLastFailedBuild().getNumber()),true));
                bentenSlackFields.add(new BentenSlackField("Is in Queue",Boolean.toString(jobWithDetails.isInQueue()),true));
                bentenSlackFields.add(new BentenSlackField("Is Buildable",Boolean.toString(jobWithDetails.isBuildable()),true));

                bentenSlackAttachment.setText(slackFormatter.build());
                bentenSlackAttachment.setBentenSlackFields(bentenSlackFields);
                bentenSlackAttachments.add(bentenSlackAttachment);

                bentenSlackResponse.setBentenSlackAttachments(bentenSlackAttachments);
            }
            else {
                bentenSlackResponse.setSlackText("Errr!! The Jenkins Job was not found.");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BentenJenkinsException(e.getMessage());
        }
        return bentenSlackResponse;
    }

    public static BentenSlackResponse renderJobBuildStatus(String buildJob) {
        BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
        if(buildJob!=null){
            bentenSlackResponse.setSlackText(buildJob);
        } else {
            bentenSlackResponse.setSlackText("Building this job failed.");
        }

        return bentenSlackResponse;
    }

    public static BentenSlackResponse renderJobConsoleLogText(String consoleLog) {

        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();

        bentenSlackResponse.setSlackText(slackFormatter
                .bold("Jenkins Job Console log: ")
                .preformatted(consoleLog)
                .build());

        return bentenSlackResponse;

    }

}
