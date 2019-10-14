package utils;

import com.intuit.benten.common.http.HttpHelper;
import com.intuit.benten.hackernews.actionhandlers.HackernewsGetBestStoriesActionHandler;
import com.intuit.benten.hackernews.actionhandlers.HackernewsGetLatestAskStoriesActionHandler;
import com.intuit.benten.hackernews.actionhandlers.HackernewsGetLatestJobStoriesActionHandler;
import com.intuit.benten.hackernews.actionhandlers.HackernewsGetLatestShowStoriesActionHandler;
import com.intuit.benten.hackernews.actionhandlers.HackernewsGetNewStoriesActionHandler;
import com.intuit.benten.hackernews.actionhandlers.HackernewsGetTopStoriesActionHandler;
import com.intuit.benten.hackernews.properties.HackernewsProperties;
import com.intuit.benten.hackernews.utils.HackernewsExecutorService;
import com.intuit.benten.hackernews.utils.HackernewsService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mock")
@EnableAutoConfiguration
public class HackernewsClientMockConfig {

    @Bean
    public HackernewsProperties hackernewsProperties() {
        HackernewsProperties props = new HackernewsProperties();
        props.setApiVersion("v0");
        props.setBaseUrl("https://hacker-news.firebaseio.com");
        return props;
    }

    @Bean
    public HttpHelper httpHelper() {
        return new HttpHelper();
    }

    @Bean
    public HackernewsService hackernewsService() {
        return new HackernewsService();
    }

    @Bean
    public HackernewsGetBestStoriesActionHandler hackernewsGetBestStoriesActionHandler() {
        return new HackernewsGetBestStoriesActionHandler();
    }

    @Bean
    public HackernewsGetNewStoriesActionHandler hackernewsGetNewStoriesActionHandler() {
        return new HackernewsGetNewStoriesActionHandler();
    }

    @Bean
    public HackernewsGetTopStoriesActionHandler hackernewsGetTopStoriesActionHandler() {
        return new HackernewsGetTopStoriesActionHandler();
    }

    @Bean
    public HackernewsGetLatestAskStoriesActionHandler hackernewsGetLatestAskStoriesActionHandler() {
        return new HackernewsGetLatestAskStoriesActionHandler();
    }

    @Bean
    public HackernewsGetLatestJobStoriesActionHandler hackernewsGetLatestJobStoriesActionHandler() {
        return new HackernewsGetLatestJobStoriesActionHandler();
    }

    @Bean
    public HackernewsGetLatestShowStoriesActionHandler hackernewsGetLatestShowStoriesActionHandler() {
        return new HackernewsGetLatestShowStoriesActionHandler();
    }

    @Bean
    public HackernewsExecutorService hackernewsExecutorService() {
        return new HackernewsExecutorService();
    }
}
