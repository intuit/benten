package com.intuit.benten.jira.http;

import com.intuit.benten.jira.JiraClientMockConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
//@SpringBootTest
@ActiveProfiles("mock")
@ContextConfiguration(classes=JiraClientMockConfig.class)
public class JiraClientTest {

    @Test
    public void dummy(){}

}
