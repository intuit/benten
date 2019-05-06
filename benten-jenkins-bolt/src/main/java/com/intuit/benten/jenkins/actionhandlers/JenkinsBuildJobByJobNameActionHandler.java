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
 * Created by sshashidhar on 2/25/18.
 */
@Component
@ActionHandler(action = JenkinsActions.ACTION_JENKINS_BUILDJOB_BY_JOBNAME)
public class JenkinsBuildJobByJobNameActionHandler implements BentenActionHandler {

    @Autowired
    private BentenJenkinsClient bentenJenkinsClient;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String jobName = BentenMessageHelper.getParameterAsString(bentenMessage,JenkinsActionParameters.PARAMETER_JOB_JOBNAME);

        String buildJob = bentenJenkinsClient.build(jobName);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        bentenHandlerResponse.setBentenSlackResponse(SlackJenkinsMessageRenderer.renderJobBuildStatus(buildJob));

        return bentenHandlerResponse;
    }
}
