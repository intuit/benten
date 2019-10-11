package com.intuit.benten.hackernews.utils;

import com.intuit.benten.common.http.HttpHelper;
import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;
import com.intuit.benten.hackernews.model.HackernewsItem;
import com.intuit.benten.hackernews.properties.HackernewsProperties;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jleveroni on 10/09/2019
 */
@Component
public class HackernewsService {
    private static final Logger logger = LoggerFactory.getLogger(HackernewsService.class);

    @Autowired
    private HttpHelper httpHelper;

    @Autowired
    private HackernewsProperties hackernewsProperties;

    @Autowired
    private HackernewsExecutorService hackernewsExecutorService;

    public List<HackernewsItem> fetchHackerNewsContent(String actionName, Integer limit, Integer offset) {
        return requestItemIds(
                HackernewsConstants.fromActionName(actionName),
                limit,
                offset);
    }

    private List<HackernewsItem> requestItemIds(String endpoint, Integer limit, Integer offset) throws BentenHackernewsException {
        String uri = buildHackernewsRequestUrl(endpoint);
        HttpGet req = new HttpGet(uri);

        try {
            HttpResponse res = httpHelper.getClient().execute(req);

            if (res.getStatusLine().getStatusCode() != 200) {
                handleFailedHackerNewsRequest(res);
            }

            String json = EntityUtils.toString(res.getEntity());
            List<Integer> hackerNewsItemIds = parseListOfIds(json, limit, offset);
            List<HackernewsItem> items = fetchHackerNewsItems(hackerNewsItemIds);
            return items;
        } catch (IOException e) {
            throw new BentenHackernewsException(e.getMessage());
        }
    }

    private String buildHackernewsRequestUrl(String ... pathSegments) {
        StringBuilder sb = new StringBuilder(hackernewsProperties.getUrlWithApiVersion());

        if (pathSegments != null) {
            for (String segment : pathSegments) {
                sb.append("/").append(segment);
            }
        }

        sb.append(HackernewsConstants.ApiEndpoints.PRETTY_PRINT);
        return sb.toString();
    }

    // Normally the json response from an api call would be deserialized with an object mapper
    // however the hacker news api returns invalid json. its raw response is literally [id1, id2, ...]
    private List<Integer> parseListOfIds(String idListJson, Integer limit, Integer offset) {
        if (limit == null) {
            limit = HackernewsConstants.HACKERNEWS_DEFAULT_ITEM_LIMIT;
        } else if (limit > HackernewsConstants.HACKERNEWS_MAX_ITEM_LIMIT) {
            logger.info(HackernewsConstants.ErrorMessages.LIMIT_EXCEEDS_MAX_LIMIT + limit);
            limit = HackernewsConstants.HACKERNEWS_MAX_ITEM_LIMIT;
        }

        if (offset == null) {
            offset = HackernewsConstants.HACKERNEWS_DEFAULT_OFFSET;
        }

        String trimmedIdList = idListJson.substring(2, idListJson.length() - 2);
        String[] stringIds = trimmedIdList.split(",");
        Integer startIndex = limit * offset;
        int stopIndex = startIndex + limit - 1;

        if (limit < 0 || offset < 0) {
            throw new BentenHackernewsException(HackernewsConstants.ErrorMessages.NEGATIVE_LIMIT_OR_OFFSET);
        }

        if (limit == 0 && offset == 0) {
            throw new BentenHackernewsException(HackernewsConstants.ErrorMessages.ACTION_LIMIT_AND_OFFSET_ZERO);
        }

        if (stopIndex > stringIds.length) {
            StringBuilder sb = new StringBuilder(HackernewsConstants.ErrorMessages.INVALID_LIMIT_AND_OFFSET_CONFIGURATION);
            sb.append("Hackernews Ids length: ").append(stringIds.length);
            sb.append("StartIndex: ").append(startIndex);
            sb.append("Offset: ").append(offset);
            throw new BentenHackernewsException(sb.toString());
        }

        String[] trimmedIdsSubset = Arrays.copyOfRange(stringIds, startIndex, stopIndex);

        return Arrays.stream(trimmedIdsSubset)
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<HackernewsItem> fetchHackerNewsItems(List<Integer> itemIds) {
        List<FetchHackernewsItemTask> tasks = itemIds.stream()
            .map(x -> new FetchHackernewsItemTask(hackernewsProperties.getUrlWithApiVersion(), x))
            .collect(Collectors.toList());
        return hackernewsExecutorService.submitFetchHackernewsItemTasks(tasks);
    }

    private void handleFailedHackerNewsRequest(HttpResponse response) {
        throw new NotImplementedException();
    }
}
