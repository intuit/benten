package com.intuit.benten.hackernews.actionnhandlers;

import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
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
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;
        Integer resultSetSize = null;
        Integer offset = null;

        List<HackernewsItem> hackernewsItems = hackernewsService.fetchHackernewsCollectionContent(action, resultSetSize, offset, null);
        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        bentenHandlerResponse.setBentenSlackResponse(SlackHackerNewsMessageRenderer.renderItemList(hackernewsItems));

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse().getSlackText());
    }

    // Negative limit
    @Test
    public void testHandleMessageWithNegativeLimit() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;
        Integer resultSetSize = -1;
        Integer offset = null;

        try {
            hackernewsService.fetchHackernewsCollectionContent(action, resultSetSize, offset, null);
        } catch (BentenHackernewsException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    // Negative offset
    @Test
    public void testHandleMessageWithNegativeOffset() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;
        Integer resultSetSize = null;
        Integer offset = -1;

        try {
            hackernewsService.fetchHackernewsCollectionContent(action, resultSetSize, offset, null);
        } catch (BentenHackernewsException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    // Negative resultSetSize and offset
    @Test
    public void testHandleMessageWithNegativeLimitAndOffset() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;
        Integer resultSetSize = -1;
        Integer offset = -1;

        try {
            hackernewsService.fetchHackernewsCollectionContent(action, resultSetSize, offset, null);
        } catch (BentenHackernewsException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    // Custom resultSetSize and default offset
    @Test
    public void testHandleMessageWithNonDefaultLimit() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;
        int resultSetSize = 15;

        List<HackernewsItem> items = hackernewsService.fetchHackernewsCollectionContent(action, resultSetSize, null, null);
        Assert.assertEquals(items.size(), resultSetSize);
    }

    // Custom resultSetSize and default offset
    @Test
    public void testHandleMessageWithCustomStartIndex() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;
        int resultSetSize = 4;
        int startIndex = 5;

        List<HackernewsItem> items = hackernewsService.fetchHackernewsCollectionContent(action, resultSetSize, null, startIndex);
        Assert.assertEquals(items.size(), resultSetSize);
    }
}
