package com.intuit.benten.hackernews.utils;

import com.intuit.benten.common.http.HttpHelper;
import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import java.util.concurrent.Callable;

public class FetchHackernewsItemTask implements Callable<HttpResponse> {
    private Integer itemId;
    private String uri;
    private HttpHelper httpHelper;

    public FetchHackernewsItemTask(String url, Integer id) {
        httpHelper = new HttpHelper();
        uri = url;
        itemId = id;
    }

    @Override
    public HttpResponse call() {
        try {
            if (uri == null || itemId == null) {
                throw new BentenHackernewsException("either the uri or itemId was null");
            }

            String sb = uri + "/"
                    + HackernewsConstants.ApiEndpoints.ITEM + "/"
                    + itemId + HackernewsConstants.ApiEndpoints.JSON
                    + HackernewsConstants.ApiEndpoints.PRETTY_PRINT;
            HttpGet request = new HttpGet(sb);
            HttpResponse res = httpHelper.getClient().execute(request);

            if (res.getStatusLine().getStatusCode() != 200) {
                throw new BentenHackernewsException(
                        HackernewsConstants.ErrorMessages.ITEM_REQUEST_FAILED + res.getStatusLine().getReasonPhrase());
            }

            return res;
        } catch (Exception e) {
            throw new BentenHackernewsException(e.getMessage());
        }
    }
}
