package com.intuit.benten.flickr.utils;

import com.flickr4java.flickr.cameras.Camera;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.intuit.benten.common.actionhandlers.BentenSlackAttachment;
import com.intuit.benten.common.actionhandlers.BentenSlackField;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.formatters.SlackFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by itstc on 2019-10-07
 */
public class SlackFlickrMessageRenderer {
    public static BentenSlackResponse renderList(PhotoList<Photo> photos) {
        BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();
        List<BentenSlackAttachment> attachments = new ArrayList<>();
        if (photos.size() > 0) {
            slackFormatter.text(String.format("Results found (%d):\n", photos.size()));
            for (Photo photo : photos) {
                BentenSlackAttachment attachment = new BentenSlackAttachment();
                attachment.setTitle(photo.getTitle());
                attachment.setTitle_link(photo.getUrl());
                attachment.setThumb_url(photo.getSmallUrl());
                attachments.add(attachment);
            }
        } else {
            slackFormatter.text("No photos found.");
        }

        bentenSlackResponse.setSlackText(slackFormatter.build());
        bentenSlackResponse.setBentenSlackAttachments(attachments);
        return bentenSlackResponse;
    }

    public static BentenSlackResponse renderList(List<Camera> cameras) {
        BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();
        List<BentenSlackAttachment> attachments = new ArrayList<>();
        if (cameras.size() > 0) {
            slackFormatter.text(String.format("Results found (%d):\n", cameras.size()));
            for (Camera camera : cameras) {
                String searchUrl = SearchUtils.generateGoogleSearchUrl(camera.getName());
                slackFormatter.link(searchUrl, camera.getName()).newline();
            }
        } else {
            slackFormatter.text("No photos found.");
        }

        bentenSlackResponse.setSlackText(slackFormatter.build());
        bentenSlackResponse.setBentenSlackAttachments(attachments);
        return bentenSlackResponse;
    }
}
