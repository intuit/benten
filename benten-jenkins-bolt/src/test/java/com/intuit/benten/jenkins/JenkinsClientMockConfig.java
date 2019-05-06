package com.intuit.benten.jenkins;

import com.intuit.benten.BentenJenkinsClient;
import com.intuit.benten.jenkins.actionhandlers.JenkinsJobDetailsByJobNameActionHandler;
import com.intuit.benten.jenkins.actionhandlers.JenkinsSearchJobByPrefixActionHandler;
import com.intuit.benten.jenkins.properties.JenkinsProperties;
import com.intuit.karate.netty.FeatureServer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;

/**
 *
 * @author divakar ungatla
 */
@Configuration
@Profile("mock")
@EnableAutoConfiguration
public class JenkinsClientMockConfig {


    @Bean
    public JenkinsProperties jenkinsProperties(FeatureServer server) {
        JenkinsProperties props = new JenkinsProperties();
        props.setBaseurl("http://localhost:" + server.getPort());
        props.setUsername("scott");
        props.setPassword("tiger");
        return props;
    }

    @Bean
    public BentenJenkinsClient bentenJenkinsClient() {
        return new BentenJenkinsClient();
    }


    @Bean
    public FeatureServer mockServer() {
        File file = new File("src/test/java/com/intuit/benten/jenkins/jenkins-mock.feature");
        FeatureServer server = FeatureServer.start(file, 8001, false, null);
        return server;
    }

    @Bean
    public  JenkinsSearchJobByPrefixActionHandler jenkinsSearchJobByPrefixActionHandler() {
        return new JenkinsSearchJobByPrefixActionHandler();
    }

    @Bean
    public JenkinsJobDetailsByJobNameActionHandler jenkinsJobDetailsByJobNameActionHandler() {
        return new JenkinsJobDetailsByJobNameActionHandler();
    }

}
