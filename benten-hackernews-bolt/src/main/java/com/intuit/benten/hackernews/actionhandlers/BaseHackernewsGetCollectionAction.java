package com.intuit.benten.hackernews.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;
import com.intuit.benten.hackernews.model.HackernewsItem;
import com.intuit.benten.hackernews.utils.HackernewsConstants;
import com.intuit.benten.hackernews.utils.HackernewsService;
import com.intuit.benten.hackernews.utils.SlackHackerNewsMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaseHackernewsGetCollectionAction {
    @Autowired
    private HackernewsService hackernewsService;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {
        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        String action = bentenMessage.getAction();
        Integer resultSetSize = BentenMessageHelper.getParameterAsInteger(bentenMessage,
                HackernewsConstants.Parameters.RESULT_SET_SIZE);
        Integer offset = BentenMessageHelper.getParameterAsInteger(bentenMessage,
                HackernewsConstants.Parameters.OFFSET);
        Integer startIndex = BentenMessageHelper.getParameterAsInteger(bentenMessage,
                HackernewsConstants.Parameters.START_INDEX);


        try {
            List<HackernewsItem> hackernewsItems = hackernewsService.fetchHackernewsCollectionContent(
                    action,
                    resultSetSize,
                    offset,
                    startIndex);
            bentenHandlerResponse.setBentenSlackResponse(
                    SlackHackerNewsMessageRenderer.renderItemList(hackernewsItems));
            return bentenHandlerResponse;
        } catch (BentenHackernewsException e) {
            bentenHandlerResponse.setBentenSlackResponse(SlackHackerNewsMessageRenderer.renderError(action, e));
            return bentenHandlerResponse;
        }
    }
}
