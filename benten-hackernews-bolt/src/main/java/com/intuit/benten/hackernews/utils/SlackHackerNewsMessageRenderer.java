package com.intuit.benten.hackernews.utils;

import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.hackernews.model.HackernewsItem;

/**
 * Created by jleveroni on 10/09/2019
 */
public class SlackHackerNewsMessageRenderer {
    public static BentenSlackResponse renderStoryItem(HackernewsItem story) {
        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();

        bentenSlackResponse.setSlackText(slackFormatter
                .bold(story.getTitle())
                .newline()
                .text(story.getText())
                .newline()
                .italic(story.getBy())
                .bold(" | Score: ")
                .italic(story.getScore().toString())
                .newline()
                .link(story.getUrl(), "View in browser")
                .build());

        return bentenSlackResponse;
    }
}
