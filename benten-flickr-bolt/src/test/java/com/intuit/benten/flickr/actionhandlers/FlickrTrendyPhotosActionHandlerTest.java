package com.intuit.benten.flickr.actionhandlers;

import com.flickr4java.flickr.cameras.Camera;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.intuit.benten.BaseFlickrTest;
import com.intuit.benten.BentenFlickrClient;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.flickr.exceptions.BentenFlickrException;
import com.intuit.benten.flickr.helpers.FlickrMessageBuilder;
import com.intuit.benten.flickr.helpers.FlickrResultBuilder;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itstc on 2019-10-17
 */
@EnableAutoConfiguration
public class FlickrTrendyPhotosActionHandlerTest extends BaseFlickrTest {
    @Autowired
    FlickrTrendyPhotosActionHandler flickrTrendyPhotosActionHandler;

    @Autowired
    BentenFlickrClient bentenFlickrClient;

    @Test
    public void testEmptyRequest() {
        Mockito.when(bentenFlickrClient.getTrendyPhotos()).thenReturn(new PhotoList<>());
        BentenHandlerResponse response = flickrTrendyPhotosActionHandler.handle(new BentenMessage());
        Assert.hasText(response.getBentenSlackResponse().getSlackText(), "Should have slack message");
    }

    @Test
    public void testPopulatedRequest() {
        PhotoList<Photo> mockResults = new PhotoList<>();
        for (int i = 0; i < 3; i++) {
            mockResults.add(new Photo());
        }
        Mockito.when(bentenFlickrClient.getTrendyPhotos()).thenReturn(mockResults);
        BentenHandlerResponse response = flickrTrendyPhotosActionHandler.handle(new BentenMessage());
        Assert.isTrue(response.getBentenSlackResponse().getBentenSlackAttachments().size() == 3, "Should have 3 attachments");
    }

    @Test
    public void testErrorRequest() {
        Mockito.when(bentenFlickrClient.getBrandCameras("Apple")).thenThrow(new BentenFlickrException("error"));
        BentenHandlerResponse response = flickrTrendyPhotosActionHandler.handle(new BentenMessage());
        Assert.hasText(response.getBentenSlackResponse().getSlackText(), "Should have slack message");
    }
}
