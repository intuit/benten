package com.intuit.benten.flickr.utils;

import com.flickr4java.flickr.cameras.Camera;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.intuit.benten.BaseFlickrTest;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.flickr.helpers.FlickrResultBuilder;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itstc on 2019-10-17
 */
@EnableAutoConfiguration
public class SlackFlickrMessageRendererTest extends BaseFlickrTest {
    @Test
    public void testRenderListForNoCameras() {
        List<Camera> mockResults = new ArrayList<>();
        BentenSlackResponse response = SlackFlickrMessageRenderer.renderList(mockResults);
        Assert.hasText(response.getSlackText(), "Should have slack message");
        Assert.isTrue(response.getBentenSlackAttachments().size() == 0, "Should have no attachments");
    }

    @Test
    public void testRenderListForCameras() {
        List<Camera> mockResults = FlickrResultBuilder.getCamerasFromJSON("camera-by-brand.json");
        BentenSlackResponse response = SlackFlickrMessageRenderer.renderList(mockResults);
        Assert.isTrue(response.getSlackText().split("\n").length == 4, "Should have 4 lines of text");
    }

    @Test
    public void testRenderListForNoPhotos() {
        PhotoList<Photo> mockResults = new PhotoList<>();
        BentenSlackResponse response = SlackFlickrMessageRenderer.renderList(mockResults);
        Assert.hasText(response.getSlackText(), "Should have slack message");
        Assert.isTrue(response.getBentenSlackAttachments().size() == 0, "Should have no attachments");
    }

    @Test
    public void testRenderListForPhotos() {
        PhotoList<Photo> mockResults = new PhotoList<>();
        for (int i = 0; i < 3; i++) {
            mockResults.add(new Photo());
        }
        BentenSlackResponse response = SlackFlickrMessageRenderer.renderList(mockResults);
        Assert.hasText(response.getSlackText(), "Should have slack message");
        Assert.isTrue(response.getBentenSlackAttachments().size() == 3, "Should have 3 attachments");
    }
}
