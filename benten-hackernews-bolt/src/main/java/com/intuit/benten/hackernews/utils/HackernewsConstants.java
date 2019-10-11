package com.intuit.benten.hackernews.utils;

import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;

public class HackernewsConstants {
    public static final int HACKERNEWS_MAX_ITEM_LIMIT = 25;
    public static final int HACKERNEWS_DEFAULT_ITEM_LIMIT = 10;
    public static final int HACKERNEWS_DEFAULT_OFFSET = 0;
    public static final int HACKERNEWS_MAX_THREADS = 25;

    public static class ApiEndpoints {
        public static final String TOP_STORIES = "topstories.json";
        public static final String NEW_STORIES = "newstories.json";
        public static final String BEST_STORIES = "beststories.json";
        public static final String LATEST_ASKS = "askstories.json";
        public static final String LATEST_SHOW = "showstories.json";
        public static final String LATEST_JOB = "jobstories.json";
        public static final String MAX_ITEM = "maxitem.json";
        public static final String ITEM = "item";
        public static final String PRETTY_PRINT = "?print=pretty";
        public static final String JSON = ".json";
    }

    public static class ErrorMessages {
        public static final String ACTION_LIMIT_AND_OFFSET_ZERO = "Both limit and offset cannot be 0";
        public static final String INVALID_LIMIT_AND_OFFSET_CONFIGURATION = "Invalid limit and offset configuration";
        public static final String NEGATIVE_LIMIT_OR_OFFSET = "Invalid negative limit or offset provided";
        public static final String LIMIT_EXCEEDS_MAX_LIMIT = "Limit exceeded max item limit of 25, value: ";
        public static final String INVALID_ACTION_NAME = "Invalid action name: ";
        public static final String ITEM_REQUEST_FAILED = "Item Call failed: ";
    }

    public static class HackernewsActions {
        public static final String ACTION_HACKERNEWS_GET_TOP_STORIES = "action_hackernews_get_top_stories";
        public static final String ACTION_HACKERNEWS_GET_NEW_STORIES = "action_hackernews_get_new_stories";
        public static final String ACTION_HACKERNEWS_GET_BEST_STORIES = "action_hackernews_get_best_stories";
        public static final String ACTION_HACKERNEWS_GET_LATEST_ASKS = "action_hackernews_latest_asks";
        public static final String ACTION_HACKERNEWS_GET_LATEST_SHOW_STORIES = "action_hackernews_get_latest_show_stories";
        public static final String ACTION_HACKERNEWS_GET_LATEST_JOB_STORIES = "action_hackernews_get_latest_job_stories";
        public static final String ACTION_HACKERNEWS_GET_NEWEST_CONTENT_ID = "action_hackernews_newest_content_id";
    }

    public static String fromActionName(String name) {
        switch(name) {
            case HackernewsActions.ACTION_HACKERNEWS_GET_TOP_STORIES:
                return HackernewsConstants.ApiEndpoints.TOP_STORIES;
            case HackernewsActions.ACTION_HACKERNEWS_GET_NEW_STORIES:
                return HackernewsConstants.ApiEndpoints.NEW_STORIES;
            case HackernewsActions.ACTION_HACKERNEWS_GET_BEST_STORIES:
                return HackernewsConstants.ApiEndpoints.BEST_STORIES;
            case HackernewsActions.ACTION_HACKERNEWS_GET_LATEST_ASKS:
                return HackernewsConstants.ApiEndpoints.LATEST_ASKS;
            case HackernewsActions.ACTION_HACKERNEWS_GET_LATEST_SHOW_STORIES:
                return HackernewsConstants.ApiEndpoints.LATEST_SHOW;
            case HackernewsActions.ACTION_HACKERNEWS_GET_LATEST_JOB_STORIES:
                return HackernewsConstants.ApiEndpoints.LATEST_JOB;
            case HackernewsActions.ACTION_HACKERNEWS_GET_NEWEST_CONTENT_ID:
                return HackernewsConstants.ApiEndpoints.MAX_ITEM;
            default:
                throw new BentenHackernewsException(HackernewsConstants.ErrorMessages.INVALID_ACTION_NAME + name);
        }
    }
}
