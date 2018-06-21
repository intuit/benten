package com.intuit.benten.jenkins;

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
//@SpringBootTest(classes={BentenJenkinsClient.class,JenkinsProperties.class,JenkinsSearchJobByPrefixActionHandler.class,JenkinsJobDetailsByJobNameActionHandler.class})
@ActiveProfiles("mock")
@ContextConfiguration(classes={JenkinsClientMockConfig.class})
@EnableAutoConfiguration
public class BaseJenkinsTest {

    @Test
    public void test(){}
}
