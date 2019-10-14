package com.intuit.benten.hackernews.actionnhandlers;

import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.hackernews.actionhandlers.HackernewsActionParameters;
import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;
import com.intuit.benten.hackernews.model.HackernewsItem;
import com.intuit.benten.hackernews.utils.HackernewsConstants;
import com.intuit.benten.hackernews.utils.HackernewsService;
import com.intuit.benten.hackernews.utils.SlackHackerNewsMessageRenderer;
import org.junit.Assert;
import org.springframework.test.context.junit4.SpringRunner;
import utils.HackernewsClientMockConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("mock")
@ContextConfiguration(classes={HackernewsClientMockConfig.class})
@EnableAutoConfiguration
public class BaseHackernewsGetCollectionActionTest {
    @Autowired
    private HackernewsService hackernewsService;

    // No limit or offset specified, only action name
    @Test
    public void testHandleBasicMessage() {
        BentenMessage bentenMessage = new BentenMessage();
        bentenMessage.setAction(HackernewsConstants.Actions.ACTION_HACKERNEWS_GET_BEST_STORIES);

        String action = bentenMessage.getAction();
        Integer limit = BentenMessageHelper.getParameterAsInteger(bentenMessage, HackernewsActionParameters.LIMIT);
        Integer offset = BentenMessageHelper.getParameterAsInteger(bentenMessage, HackernewsActionParameters.OFFSET);

        List<HackernewsItem> hackernewsItems = hackernewsService.fetchHackernewsContent(action, limit, offset);
        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        bentenHandlerResponse.setBentenSlackResponse(SlackHackerNewsMessageRenderer.renderItemList(hackernewsItems));

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse().getSlackText());
    }

    // Negative limit
    @Test
    public void testHandleMessageWithNegativeLimit() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_GET_BEST_STORIES;
        Integer limit = -1;
        Integer offset = null;

        try {
            hackernewsService.fetchHackernewsContent(action, limit, offset);
        } catch (BentenHackernewsException e) {
            Assert.assertEquals(e.getMessage(), HackernewsConstants.ErrorMessages.NEGATIVE_LIMIT_OR_OFFSET);
        }
    }

    // Negative offset
    @Test
    public void testHandleMessageWithNegativeOffset() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_GET_BEST_STORIES;
        Integer limit = null;
        Integer offset = -1;

        try {
            hackernewsService.fetchHackernewsContent(action, limit, offset);
        } catch (BentenHackernewsException e) {
            Assert.assertEquals(e.getMessage(), HackernewsConstants.ErrorMessages.NEGATIVE_LIMIT_OR_OFFSET);
        }
    }

    @Test
    public void testHandleMessageWithNegativeLimitAndOffset() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_GET_BEST_STORIES;
        Integer limit = -1;
        Integer offset = -1;

        try {
            hackernewsService.fetchHackernewsContent(action, limit, offset);
        } catch (BentenHackernewsException e) {
            Assert.assertEquals(e.getMessage(), HackernewsConstants.ErrorMessages.NEGATIVE_LIMIT_OR_OFFSET);
        }
    }
}
