package com.intuit.benten.flickr.actionhandlers;

import com.flickr4java.flickr.cameras.Camera;
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
 * Created by itstc on 2019-10-15
 */
@EnableAutoConfiguration
public class FlickrBrandCamerasActionHandlerTest extends BaseFlickrTest {

    @Autowired
    FlickrBrandCamerasActionHandler flickrBrandCamerasActionHandler;

    @Autowired
    BentenFlickrClient bentenFlickrClient;

    @Test
    public void testEmptyRequest() {
        Mockito.when(bentenFlickrClient.getBrandCameras("Apple")).thenReturn(new ArrayList<>());
        BentenMessage params = FlickrMessageBuilder.constructBrandCamerasMessage("Apple");
        BentenHandlerResponse response = flickrBrandCamerasActionHandler.handle(params);
        Assert.hasText(response.getBentenSlackResponse().getSlackText(), "Should have slack message");
    }

    @Test
    public void testPopulatedRequest() {
        List<Camera> mockResults = FlickrResultBuilder.getCamerasFromJSON("camera-by-brand.json");
        Mockito.when(bentenFlickrClient.getBrandCameras("Apple")).thenReturn(mockResults);
        BentenMessage params = FlickrMessageBuilder.constructBrandCamerasMessage("Apple");
        BentenHandlerResponse response = flickrBrandCamerasActionHandler.handle(params);
        Assert.isTrue(response.getBentenSlackResponse().getSlackText().split("\n").length == 4, "Should have 4 lines of text");
    }

    @Test
    public void testErrorRequest() {
        Mockito.when(bentenFlickrClient.getBrandCameras("Apple")).thenThrow(new BentenFlickrException("error"));
        BentenMessage params = FlickrMessageBuilder.constructBrandCamerasMessage("Apple");
        BentenHandlerResponse response = flickrBrandCamerasActionHandler.handle(params);
        Assert.hasText(response.getBentenSlackResponse().getSlackText(), "Should have slack message");
    }
}
