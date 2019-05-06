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

import java.util.Map;

/**
 * Created by sshashidhar on 2/24/18.
 */
@Component
@ActionHandler(action = JenkinsActions.ACTION_JENKINS_SEARCH_BUILDS_BY_PREFIX)
public class JenkinsSearchJobByPrefixActionHandler implements BentenActionHandler {

    @Autowired
    private BentenJenkinsClient bentenJenkinsClient;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String jobPrefix = BentenMessageHelper.getParameterAsString(bentenMessage,JenkinsActionParameters.PARAMETER_JOB_PREFIX);

        Map<String, String> jobList = bentenJenkinsClient.getAllJobsWithPrefix(jobPrefix);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        bentenHandlerResponse.setBentenSlackResponse(SlackJenkinsMessageRenderer.renderJobList(jobList.values()));

        return bentenHandlerResponse;
    }
}
