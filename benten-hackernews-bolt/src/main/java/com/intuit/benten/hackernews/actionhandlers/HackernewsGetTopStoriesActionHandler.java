package com.intuit.benten.hackernews.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.hackernews.model.HackernewsItem;
import com.intuit.benten.hackernews.utils.HackernewsConstants;
import com.intuit.benten.hackernews.utils.HackernewsService;
import com.intuit.benten.hackernews.utils.SlackHackerNewsMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jleveroni on 10/09/2019
 */
@Component
@ActionHandler(action = HackernewsConstants.HackernewsActions.ACTION_HACKERNEWS_GET_TOP_STORIES)
public class HackernewsGetTopStoriesActionHandler implements BentenActionHandler {
    @Autowired
    private HackernewsService hackernewsService;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        String action = bentenMessage.getAction();
        Integer limit = BentenMessageHelper.getParameterAsInteger(bentenMessage, HackernewsActionParameters.LIMIT);
        Integer offset = BentenMessageHelper.getParameterAsInteger(bentenMessage, HackernewsActionParameters.OFFSET);

        List<HackernewsItem> hackernewsItems = hackernewsService.fetchHackerNewsContent(action, limit, offset);
        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        bentenHandlerResponse.setBentenSlackResponse(
                SlackHackerNewsMessageRenderer.renderItemList(hackernewsItems));
        return bentenHandlerResponse;
    }
}
