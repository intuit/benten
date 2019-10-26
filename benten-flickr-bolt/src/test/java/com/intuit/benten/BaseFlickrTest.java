package com.intuit.benten;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by itstc on 2019-10-15
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("mock")
@ContextConfiguration(classes={FlickrClientMockConfig.class})
@EnableAutoConfiguration
public class BaseFlickrTest {

    @Test
    public void test(){}
}
