package com.intuit.benten.hackernews.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;
import com.intuit.benten.hackernews.model.HackernewsItem;
import org.apache.http.util.EntityUtils;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.apache.http.HttpResponse;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.List;

@Component
public class HackernewsExecutorService {
    private ExecutorService executorService = Executors.newFixedThreadPool(HackernewsConstants.HACKERNEWS_MAX_THREADS);
    private ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);

    public List<HackernewsItem> submitFetchHackernewsItemTasks(List<FetchHackernewsItemTask> tasks) {
        try {
            List<Future<HttpResponse>> futures = executorService.invokeAll(tasks);
            return futures.stream().map(x -> {
                try {
                    String json = EntityUtils.toString(x.get().getEntity());
                    return objectMapper.readValue(json, HackernewsItem.class);
                } catch (Exception e) {
                    throw new BentenHackernewsException(e.getMessage());
                }
            }).collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new BentenHackernewsException(e.getMessage());
        }

    }

}
