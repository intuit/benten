
package com.intuit.benten.flickr.actionhandlers;


import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.intuit.benten.BentenFlickrClient;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.flickr.utils.SlackFlickrMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by itstc on 2019-10-07
 */
@Component
@ActionHandler(action = FlickrActions.ACTION_FLICKR_SEARCH_PHOTOS)
public class FlickrSearchPhotosActionHandler implements BentenActionHandler {
    @Autowired
    private BentenFlickrClient bentenFlickrClient;

    @Override
    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String mediaType = BentenMessageHelper.getParameterAsString(bentenMessage,FlickrActionParamaters.PARAMETER_MEDIA_TYPE);
        String searchValue = BentenMessageHelper.getParameterAsString(bentenMessage,FlickrActionParamaters.PARAMETER_SEARCH_VALUE);
        Integer searchLimit = BentenMessageHelper.getParameterAsInteger(bentenMessage,FlickrActionParamaters.PARAMETER_SEARCH_LIMIT);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        try {
            PhotoList<Photo> response = bentenFlickrClient.searchPhotos(mediaType, searchValue, searchLimit);
            bentenHandlerResponse.setBentenSlackResponse(SlackFlickrMessageRenderer.renderList(response));
        } catch (Exception e) {
            BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
            bentenSlackResponse.setSlackText("Unable to search for photos");
            bentenHandlerResponse.setBentenSlackResponse(bentenSlackResponse);
        }

        return bentenHandlerResponse;
    }
}
