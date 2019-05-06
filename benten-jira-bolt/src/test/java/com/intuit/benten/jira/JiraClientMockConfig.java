package com.intuit.benten.jira;

import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.jira.actionhandlers.helpers.CycleTimeCalculator;
import com.intuit.benten.jira.conversationcatalysts.JiraCreateIssueConversationCatalyst;
import com.intuit.benten.jira.http.JiraAgileHttpClient;
import com.intuit.benten.jira.http.JiraGhClient;
import com.intuit.benten.jira.http.JiraHttpClient;
import com.intuit.benten.jira.actionhandlers.*;
import com.intuit.benten.jira.properties.JiraProperties;
import com.intuit.benten.common.http.HttpHelper;
import com.intuit.karate.netty.FeatureServer;
import java.io.File;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author pthomas3
 */
@Configuration
@Profile("mock")
@ComponentScan(value = "com.intuit.benten.jira.http")
@EnableAutoConfiguration
public class JiraClientMockConfig {
    
    @Bean
    public HttpHelper httpHelper() {
        return new HttpHelper();
    }
    
    @Bean
    public FeatureServer mockServer() {
        File file = new File("src/test/java/com/intuit/benten/jira/jira-mock.feature");
        FeatureServer server = FeatureServer.start(file, 0, false, null);
        return server;
    }
    
    @Bean
    public JiraProperties jiraProperties(FeatureServer server) {
        JiraProperties props = new JiraProperties();
        props.setBaseurl("http://localhost:" + server.getPort());        
        props.setUsername("scott");
        props.setPassword("tiger");
        props.setHerokuurl(null);
        return props;
    }
    
    @Bean
    public BentenJiraClient bentenJiraClient() {
        return new BentenJiraClient();
    }

    @Bean
    public JiraAgileHttpClient jiraAgileHttpClient() {
        return new JiraAgileHttpClient();
    }

    @Bean
    public JiraGhClient jiraGhClient() {
        return new JiraGhClient();
    }

    @Bean
    public JiraHttpClient jiraHttpClient() {
        return new JiraHttpClient();
    }

    @Bean
    public JiraCycleTimeActionHandler jiraCycleTimeActionHandler() {
        return new JiraCycleTimeActionHandler();
    }

    @Bean
    public JiraSprintVelocityActionHandler jiraSprintVelocityActionHandler() {
        return new JiraSprintVelocityActionHandler();
    }

    @Bean
    public CycleTimeCalculator cycleTimeCalculator() {
        return new CycleTimeCalculator();
    }

    @Bean
    public JiraCommentActionHandler jiraCommentActionHandler(){
        return new JiraCommentActionHandler();
    }

    @Bean
    public JiraAssignIssueToUserActionHandler jiraAssignIssueToUserActionHandler(){
        return new JiraAssignIssueToUserActionHandler();
    }

    @Bean
    public JiraCreateIssueActionHandler jiraCreateIssueActionHandler(){
        return new JiraCreateIssueActionHandler();
    }

    @Bean
    public JiraLogWorkActionHandler jiraLogWorkActionHandler(){
        return new JiraLogWorkActionHandler();
    }

    @Bean
    public JiraSearchIssuesByUserActionHandler jiraSearchIssuesByUserActionHandler(){
        return new JiraSearchIssuesByUserActionHandler();
    }

    @Bean
    public JiraIssueDetailsActionHandler jiraIssueDetailsActionHandler(){
        return new JiraIssueDetailsActionHandler();
    }

    @Bean
    public JiraTransitionIssueActionHandler jiraTransitionIssueActionHandler(){
        return new JiraTransitionIssueActionHandler();
    }

    @Bean
    public JiraCreateIssueConversationCatalyst jiraCreateIssueConversationCatalyst(){
        return new JiraCreateIssueConversationCatalyst();
    }

    
}
