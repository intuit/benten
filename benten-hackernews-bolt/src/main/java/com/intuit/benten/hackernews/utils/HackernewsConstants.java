package com.intuit.benten.hackernews.utils;

import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;

public class HackernewsConstants {
    static final int HACKERNEWS_MAX_ITEM_LIMIT = 25;
    static final int HACKERNEWS_DEFAULT_OFFSET = 0;
    static final int HACKERNEWS_MAX_THREADS = 25;
    public static final int HACKERNEWS_DEFAULT_ITEM_LIMIT = 10;

    static class ApiEndpoints {
        static final String TOP_STORIES = "topstories.json";
        static final String NEW_STORIES = "newstories.json";
        static final String BEST_STORIES = "beststories.json";
        static final String LATEST_ASKS = "askstories.json";
        static final String LATEST_SHOW = "showstories.json";
        static final String LATEST_JOB = "jobstories.json";
        static final String ITEM = "item";
        static final String PRETTY_PRINT = "?print=pretty";
        static final String JSON = ".json";
        static final String HACKERNEWS_ITEM_URL = "https://news.ycombinator.com/item?id=";

        static String fromActionName(String name) {
            switch(name) {
                // TEST actions run using the /topstories endpoint since it seems reliable
                case Actions.ACTION_HACKERNEWS_GET_TOP_STORIES:
                case Actions.ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION:
                    return HackernewsConstants.ApiEndpoints.TOP_STORIES;
                case Actions.ACTION_HACKERNEWS_GET_NEW_STORIES:
                    return HackernewsConstants.ApiEndpoints.NEW_STORIES;
                case Actions.ACTION_HACKERNEWS_GET_BEST_STORIES:
                    return HackernewsConstants.ApiEndpoints.BEST_STORIES;
                case Actions.ACTION_HACKERNEWS_GET_LATEST_ASKS:
                    return HackernewsConstants.ApiEndpoints.LATEST_ASKS;
                case Actions.ACTION_HACKERNEWS_GET_LATEST_SHOW_STORIES:
                    return HackernewsConstants.ApiEndpoints.LATEST_SHOW;
                case Actions.ACTION_HACKERNEWS_GET_LATEST_JOB_STORIES:
                    return HackernewsConstants.ApiEndpoints.LATEST_JOB;
                default:
                    throw new BentenHackernewsException(HackernewsConstants.ErrorMessages.INVALID_ACTION_NAME + name);
            }
        }
    }

    static class ErrorMessages {
        static final String INVALID_LIMIT_AND_OFFSET_CONFIGURATION = "Invalid limit and offset configuration";
        static final String LIMIT_EXCEEDS_MAX_LIMIT = "Limit exceeded max item limit of 25, value: ";
        static final String INVALID_ACTION_NAME = "Invalid action name: ";
        static final String ITEM_REQUEST_FAILED = "Item Call failed: ";
    }

    public static class Actions {
        public static final String ACTION_HACKERNEWS_GET_TOP_STORIES = "action_hackernews_get_top_stories";
        public static final String ACTION_HACKERNEWS_GET_NEW_STORIES = "action_hackernews_get_new_stories";
        public static final String ACTION_HACKERNEWS_GET_BEST_STORIES = "action_hackernews_get_best_stories";
        public static final String ACTION_HACKERNEWS_GET_LATEST_ASKS = "action_hackernews_latest_asks";
        public static final String ACTION_HACKERNEWS_GET_LATEST_SHOW_STORIES = "action_hackernews_get_latest_show_stories";
        public static final String ACTION_HACKERNEWS_GET_LATEST_JOB_STORIES = "action_hackernews_get_latest_job_stories";

        // Test action used only for automated testing
        public static final String ACTION_HACKERNEWS_TEST_FETCH_COLLECTION_ACTION = "action_hackernews_test";
    }

    public static class Parameters {
        public static final String RESULT_SET_SIZE = "resultSetSize";
        public static final String OFFSET = "offset";
        public static final String START_INDEX = "startIndex";
    }
}
