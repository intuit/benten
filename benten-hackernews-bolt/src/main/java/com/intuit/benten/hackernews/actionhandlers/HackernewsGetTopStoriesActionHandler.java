package com.intuit.benten.hackernews.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.hackernews.model.HackernewsItem;
import com.intuit.benten.hackernews.utils.HackernewsService;
import com.intuit.benten.hackernews.utils.SlackHackerNewsMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jleveroni on 10/09/2019
 */
@Component
@ActionHandler(action = HackernewsActions.ACTION_HACKERNEWS_GET_TOP_STORIES)
public class HackernewsGetTopStoriesActionHandler implements BentenActionHandler {
    @Autowired
    private HackernewsService hackernewsService;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        HackernewsItem hackernewsItem = hackernewsService.getTopStories();
        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        bentenHandlerResponse.setBentenSlackResponse(
                SlackHackerNewsMessageRenderer.renderStoryItem(hackernewsItem));
        return bentenHandlerResponse;
    }
}
