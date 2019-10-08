package com.intuit.benten;

import com.intuit.benten.flickr.actionhandlers.FlickrBrandCamerasActionHandler;
import com.intuit.benten.flickr.actionhandlers.FlickrSearchPhotosActionHandler;
import com.intuit.benten.flickr.actionhandlers.FlickrTrendyPhotosActionHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by itstc on 2019-10-15
 */
@Configuration
@Profile("mock")
@EnableAutoConfiguration
public class FlickrClientMockConfig {
    @MockBean
    BentenFlickrClient bentenFlickrClient;

    @Bean
    public FlickrSearchPhotosActionHandler flickrSearchPhotosActionHandler() {
        return new FlickrSearchPhotosActionHandler();
    }

    @Bean
    public FlickrTrendyPhotosActionHandler flickrTrendyPhotosActionHandler() {
        return new FlickrTrendyPhotosActionHandler();
    }

    @Bean
    public FlickrBrandCamerasActionHandler flickrBrandCamerasActionHandler() {
        return new FlickrBrandCamerasActionHandler();
    }
}
