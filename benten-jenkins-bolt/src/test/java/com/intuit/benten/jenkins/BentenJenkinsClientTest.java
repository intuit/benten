package com.intuit.benten.jenkins;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intuit.benten.BentenJenkinsClient;
import com.intuit.benten.jenkins.model.JenkinsJobBuildParameter;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;


/**
 * Created by dungatla.
 */

@EnableAutoConfiguration
public class BentenJenkinsClientTest extends BaseJenkinsTest{

    @Autowired
    BentenJenkinsClient bentenJenkinsClient;

    @Test
    public void testJenkinsServerConnection(){
        Assert.assertTrue(bentenJenkinsClient.getJenkins().isRunning());
    }

    @Test
    public void testGetJobByJobName() throws JsonProcessingException {
        String jobName = "BenTen-Env-Stability";
        JobWithDetails jobWithDetails = bentenJenkinsClient.getJobByJobName(jobName);
        String jobNameFromJenkins = jobWithDetails.getDisplayName();
        Assert.assertEquals("Build Names dont match",jobName, jobNameFromJenkins);
    }

    @Test
    public void testGetAllJobsWithPrefix(){
        Assert.assertNotNull(bentenJenkinsClient.getAllJobsWithPrefix("BenTen"));
    }

    @Test
    public void testBuildJob(){
        String jobName = "BenTen-Env-Stability";
        Assert.assertNotNull(bentenJenkinsClient.build(jobName));
    }

    @Test
    @Ignore
    public void testConsoleOutputForBuild(){
        String jobName = "BenTen-Env-Stability";
        int buildNumber = 344;
        Assert.assertNotNull(bentenJenkinsClient.showConsoleLogForJobWithBuildNumber(jobName,buildNumber));
    }

    @Test
    public void testGetBuildParams(){
        String jobName = "A-release";

        List<JenkinsJobBuildParameter> a = bentenJenkinsClient.getBuildParams(jobName);
        Assert.assertTrue(a.size()>0);
        a.forEach(item -> {
            System.out.println("Name: " + item.getName());
            System.out.println("Default Value: " + item.getDefaultValue());
            System.out.println("Choices: " + item.getChoices());
        });

        System.out.println("---------------------------------------------------------------------------------------------------");

    }
}
