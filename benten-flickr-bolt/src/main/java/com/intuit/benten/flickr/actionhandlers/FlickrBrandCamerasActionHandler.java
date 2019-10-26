package com.intuit.benten.flickr.actionhandlers;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.cameras.Camera;
import com.intuit.benten.BentenFlickrClient;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.flickr.exceptions.BentenFlickrException;
import com.intuit.benten.flickr.utils.SlackFlickrMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by itstc on 2019-10-11
 */
@Component
@ActionHandler(action=FlickrActions.ACTION_FLICKR_BRAND_CAMERAS)
public class FlickrBrandCamerasActionHandler implements BentenActionHandler {

    @Autowired
    BentenFlickrClient bentenFlickrClient;

    @Override
    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        BentenHandlerResponse handlerResponse = new BentenHandlerResponse();
        String brandName = BentenMessageHelper.getParameterAsString(bentenMessage, FlickrActionParamaters.PARAMETER_BRAND_NAME);
        try {
            List<Camera> result = bentenFlickrClient.getBrandCameras(brandName);
            handlerResponse.setBentenSlackResponse(SlackFlickrMessageRenderer.renderList(result));
        } catch (BentenFlickrException e) {
            BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
            bentenSlackResponse.setSlackText(String.format("No cameras found for %s", brandName));
            handlerResponse.setBentenSlackResponse(bentenSlackResponse);
        }

        return handlerResponse;
    }

}
