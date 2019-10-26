package com.intuit.benten.flickr.actionhandlers;

import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.intuit.benten.BentenFlickrClient;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.flickr.utils.SlackFlickrMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by itstc on 2019-10-10
 */
@Component
@ActionHandler(action=FlickrActions.ACTION_FLICKR_TRENDY_PHOTOS)
public class FlickrTrendyPhotosActionHandler implements BentenActionHandler {

    @Autowired
    BentenFlickrClient bentenFlickrClient;

    @Override
    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        try {
            PhotoList<Photo> response = bentenFlickrClient.getTrendyPhotos();
            bentenHandlerResponse.setBentenSlackResponse(SlackFlickrMessageRenderer.renderList(response));
        } catch(Exception e) {
            BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
            bentenSlackResponse.setSlackText("Unable to retrieve trendy photos.");
            bentenHandlerResponse.setBentenSlackResponse(bentenSlackResponse);
        }

        return bentenHandlerResponse;
    }
}
