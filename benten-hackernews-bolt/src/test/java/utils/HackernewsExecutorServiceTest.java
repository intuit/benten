package utils;

import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;
import com.intuit.benten.hackernews.model.HackernewsItem;
import com.intuit.benten.hackernews.properties.HackernewsProperties;
import com.intuit.benten.hackernews.utils.FetchHackernewsItemTask;
import com.intuit.benten.hackernews.utils.HackernewsExecutorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("mock")
@ContextConfiguration(classes={HackernewsClientMockConfig.class})
@EnableAutoConfiguration
public class HackernewsExecutorServiceTest {
    private static int requestCount = 3;

    @Autowired
    HackernewsExecutorService hackernewsExecutorService;

    @Autowired
    HackernewsProperties hackernewsProperties;

    private List<FetchHackernewsItemTask> generateValidTasks() {
        List<FetchHackernewsItemTask> tasks = new LinkedList<>();

        for (int i = 0; i < requestCount; ++i) {
            tasks.add(new FetchHackernewsItemTask(hackernewsProperties.getUrlWithApiVersion(), i));
        }
        return tasks;
    }

    private List<FetchHackernewsItemTask> generateValidTasksWithInvalidTaskIncluded() {
        List<FetchHackernewsItemTask> tasks = new LinkedList<>();

        for (int i = 0; i < requestCount; ++i) {
            tasks.add(new FetchHackernewsItemTask(hackernewsProperties.getUrlWithApiVersion(), i));
        }

        tasks.add(new FetchHackernewsItemTask(hackernewsProperties.getBaseUrl(), null));
        return tasks;
    }

    @Test
    public void testFetchValidCollection() {
        List<FetchHackernewsItemTask> tasks = generateValidTasks();
        List<HackernewsItem> items = hackernewsExecutorService.submitFetchHackernewsItemTasks(tasks);
        Assert.assertEquals(items.size(), requestCount);
    }

    @Test
    public void testFetchInvalidCollection() {
        List<FetchHackernewsItemTask> tasks = generateValidTasksWithInvalidTaskIncluded();
        try {
            hackernewsExecutorService.submitFetchHackernewsItemTasks(tasks);
        } catch (BentenHackernewsException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }
}
