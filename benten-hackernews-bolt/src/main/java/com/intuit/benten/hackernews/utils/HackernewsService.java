package com.intuit.benten.hackernews.utils;

import com.intuit.benten.common.http.HttpHelper;
import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;
import com.intuit.benten.hackernews.model.HackernewsItem;
import com.intuit.benten.hackernews.model.HackernewsSetRange;
import com.intuit.benten.hackernews.properties.HackernewsProperties;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    public List<HackernewsItem> fetchHackernewsCollectionContent(String actionName,
                                                                 Integer resultSetSize,
                                                                 Integer offset,
                                                                 Integer startIndex) throws BentenHackernewsException {
        return requestItemIds(HackernewsConstants.ApiEndpoints.fromActionName(actionName),
                resultSetSize, offset, startIndex);
    }

    private List<HackernewsItem> requestItemIds(String endpoint,
                                                Integer resultSetSize,
                                                Integer offset,
                                                Integer startIndex) throws BentenHackernewsException {
        String uri = buildHackernewsRequestUrl(endpoint);
        HttpGet req = new HttpGet(uri);

        try {
            HttpResponse res = httpHelper.getClient().execute(req);

            if (res.getStatusLine().getStatusCode() != 200) {
                String message = "Response code " + res.getStatusLine().getStatusCode();
                throw new BentenHackernewsException(message);
            }

            String json = EntityUtils.toString(res.getEntity());
            List<Integer> hackerNewsItemIds = parseListOfIds(json, resultSetSize, offset, startIndex);
            return fetchHackerNewsItems(hackerNewsItemIds);
        } catch (IOException e) {
            throw new BentenHackernewsException("requestItemIds result could not be handled", e.getMessage());
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
    // so we need to do some string manipulation to work with it
    private List<Integer> parseListOfIds(String idListJson, Integer resultSetSize, Integer offset, Integer startIndex) {
        boolean isOffsetBased = true;
        if (resultSetSize == null || 0 == resultSetSize || 0 > resultSetSize) {
            resultSetSize = HackernewsConstants.HACKERNEWS_DEFAULT_ITEM_LIMIT;
        } else if (resultSetSize > HackernewsConstants.HACKERNEWS_MAX_ITEM_LIMIT) {
            logger.info(HackernewsConstants.ErrorMessages.LIMIT_EXCEEDS_MAX_LIMIT + resultSetSize);
            resultSetSize = HackernewsConstants.HACKERNEWS_MAX_ITEM_LIMIT;
        }

        // default to offset if it is set, or if both offset and startIndex are set
        if (offset == null && startIndex != null) {
            isOffsetBased = false;
        } else {
            if (offset == null || 0 > offset) {
                offset = HackernewsConstants.HACKERNEWS_DEFAULT_OFFSET;
            }
        }

        String trimmedIdList = idListJson.substring(2, idListJson.length() - 2);
        String[] stringIds = trimmedIdList.split(",");
        HackernewsSetRange floorAndCeilingRange;

        if (isOffsetBased) {
            floorAndCeilingRange = calculateCollectionRangeByOffset(resultSetSize, offset);
        } else {
            if ((startIndex - 1) < 0) {
                throw new BentenHackernewsException("Start index cannot be less than 1.");
            } else {
                floorAndCeilingRange = calculateCollectionRangeByStartIndex(resultSetSize, startIndex);
            }
        }

        Integer floor = floorAndCeilingRange.getMin();
        Integer ceiling = floorAndCeilingRange.getMax();

        if (ceiling > stringIds.length) {
            String sb = HackernewsConstants.ErrorMessages.INVALID_LIMIT_AND_OFFSET_CONFIGURATION
                    + "Hackernews Ids length: "
                    + stringIds.length
                    + "StartIndex: " + floor
                    + "Offset: " + offset;
            throw new BentenHackernewsException(sb);
        }

        String[] trimmedIdsSubset = Arrays.copyOfRange(stringIds, floor, ceiling);

        return Arrays.stream(trimmedIdsSubset)
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private HackernewsSetRange calculateCollectionRangeByOffset(Integer size, Integer offset) {
        Integer startIndex = (size * offset);
        int stopIndex = (startIndex + size);
        return new HackernewsSetRange(startIndex, stopIndex);
    }

    private HackernewsSetRange calculateCollectionRangeByStartIndex(Integer size, Integer startIndex) {
        int stopIndex = (startIndex + size);
        return new HackernewsSetRange(startIndex, stopIndex);
    }

    private List<HackernewsItem> fetchHackerNewsItems(List<Integer> itemIds) {
        List<FetchHackernewsItemTask> tasks = itemIds.stream()
            .map(x -> new FetchHackernewsItemTask(hackernewsProperties.getUrlWithApiVersion(), x))
            .collect(Collectors.toList());
        return hackernewsExecutorService.submitFetchHackernewsItemTasks(tasks);
    }
}
