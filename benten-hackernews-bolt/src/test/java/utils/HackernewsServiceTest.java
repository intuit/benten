package utils;

import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;
import com.intuit.benten.hackernews.model.HackernewsItem;
import com.intuit.benten.hackernews.utils.HackernewsConstants;
import com.intuit.benten.hackernews.utils.HackernewsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("mock")
@ContextConfiguration(classes={HackernewsClientMockConfig.class})
@EnableAutoConfiguration
public class HackernewsServiceTest {
    @Autowired
    HackernewsService hackernewsService;

    // Default resultSetSize should be 10, offset will get set to 0 overriding startIndex
    @Test
    public void testValidFetchHackernewsContent() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;

        List<HackernewsItem> items = hackernewsService.fetchHackernewsCollectionContent(action, null, null, null);
        Assert.assertEquals(items.size(), HackernewsConstants.HACKERNEWS_DEFAULT_ITEM_LIMIT);
    }

    // Default resultSetSize should be 10, offset will get set to 0 overriding startIndex
    @Test
    public void testInvalidNegativeLimitFetchHackernewsContentValid() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;

        try {
            hackernewsService.fetchHackernewsCollectionContent(action, -1, null, null);
        } catch (BentenHackernewsException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    // Default resultSetSize should be 10, offset will get set to 0 overriding startIndex
    @Test
    public void testInvalidNegativeOffsetFetchHackernewsContentValid() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;

        try {
            hackernewsService.fetchHackernewsCollectionContent(action, null, -1, null);
        } catch (BentenHackernewsException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    // Default resultSetSize should be 10, offset will get set to 0 overriding startIndex
    @Test
    public void testInvalidNegativeOffsetAndLimitFetchHackernewsContentValid() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;

        try {
            hackernewsService.fetchHackernewsCollectionContent(action, -1, -1, null);
        } catch (BentenHackernewsException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    // set resultSetSize to 4, no offset, startIndex = 6, should retunr array size of 6
    @Test
    public void testInvalidCustomResultSetSizeAndStartIndex() {
        String action = HackernewsConstants.Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION;
        int resultSetSize = 4;
        List<HackernewsItem> items = hackernewsService.fetchHackernewsCollectionContent(action, resultSetSize, null, 6);
        Assert.assertEquals(items.size(), resultSetSize);
    }
}
