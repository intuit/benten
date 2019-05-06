package com.intuit.benten.jenkins.actionhandlers;

import com.intuit.benten.BentenJenkinsClient;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jenkins.utils.SlackJenkinsMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sshashidhar on 18/06/18.
 */
@Component
@ActionHandler(action = JenkinsActions.ACTION_JENKINS_SHOWCONSOLELOG_FORBUILDNUMBER)
public class JenkinsShowConsoleLogForBuildNumberActionHandler implements BentenActionHandler {

    @Autowired
    private BentenJenkinsClient bentenJenkinsClient;

    @Override
    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String jobName = BentenMessageHelper.getParameterAsString(bentenMessage,JenkinsActionParameters.PARAMETER_JOB_JOBNAME);

        int buildNumber = bentenJenkinsClient.getLatestBuildNumber(jobName);
        String buildConsoleLog = bentenJenkinsClient.showConsoleLogForJobWithBuildNumber(jobName,buildNumber);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        bentenHandlerResponse.setBentenSlackResponse(SlackJenkinsMessageRenderer.renderJobConsoleLogText(buildConsoleLog));

        return bentenHandlerResponse;
    }
}
