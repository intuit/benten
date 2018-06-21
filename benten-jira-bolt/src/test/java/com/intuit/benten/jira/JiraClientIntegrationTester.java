package com.intuit.benten.jira;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pthomas3
 */
//@RunWith(SpringRunner.class)
//@ActiveProfiles("secret")
//@EnableAutoConfiguration
//@SpringBootTest
public class JiraClientIntegrationTester extends AbstractJiraClientTest {
    
    @Autowired
    private BentenJiraClient client;

    @Override
    protected BentenJiraClient getClient() {
        return client;
    }     
    
}
