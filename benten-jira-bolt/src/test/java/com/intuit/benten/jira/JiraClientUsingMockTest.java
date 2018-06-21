package com.intuit.benten.jira;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pthomas3
 */
//@RunWith(SpringRunner.class)
//@ActiveProfiles("mock")
//@ContextConfiguration(classes = JiraClientMockConfig.class)
//@EnableAutoConfiguration
public class JiraClientUsingMockTest extends AbstractJiraClientTest {

    @Autowired
    private BentenJiraClient client;

    @Override
    protected BentenJiraClient getClient() {
        return client;
    }        
    
}
