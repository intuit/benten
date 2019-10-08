package com.intuit.benten.flickr.actionhandlers;

import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.intuit.benten.BaseFlickrTest;
import com.intuit.benten.BentenFlickrClient;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.flickr.exceptions.BentenFlickrException;
import com.intuit.benten.flickr.helpers.FlickrMessageBuilder;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.Assert;

/**
 * Created by itstc on 2019-10-16
 */
@EnableAutoConfiguration
public class FlickrSearchPhotosActionHandlerTest extends BaseFlickrTest {
    @Autowired
    FlickrSearchPhotosActionHandler flickrSearchPhotosActionHandler;

    @Autowired
    BentenFlickrClient bentenFlickrClient;

    @Test
    public void testEmptyRequest() {
        Mockito.when(bentenFlickrClient.searchPhotos("all", "cats", 0)).thenReturn(new PhotoList<>());
        BentenMessage params = FlickrMessageBuilder.constructSearchPhotosMessage("all", "cats", "0");
        BentenHandlerResponse response = flickrSearchPhotosActionHandler.handle(params);
        Assert.hasText(response.getBentenSlackResponse().getSlackText(), "Should have slack message");
    }

    @Test
    public void testPopulatedRequest() {
        PhotoList<Photo> mockResults = new PhotoList<>();
        for (int i = 0; i < 3; i++) {
           mockResults.add(new Photo());
        }

        Mockito.when(bentenFlickrClient.searchPhotos("all", "cats", 3)).thenReturn(mockResults);
        BentenMessage params = FlickrMessageBuilder.constructSearchPhotosMessage("all", "cats", "3");
        BentenHandlerResponse response = flickrSearchPhotosActionHandler.handle(params);
        Assert.isTrue(response.getBentenSlackResponse().getBentenSlackAttachments().size() == 3, "Should have 3 attachments");
    }

    @Test
    public void testErrorRequest() {
        Mockito.when(bentenFlickrClient
                .searchPhotos("all", "cats", 3))
                .thenThrow(new BentenFlickrException("error"));
        BentenMessage params = FlickrMessageBuilder.constructSearchPhotosMessage("all", "cats", "3");
        BentenHandlerResponse response = flickrSearchPhotosActionHandler.handle(params);
        Assert.hasText(response.getBentenSlackResponse().getSlackText(), "Should have slack message");
    }
}
