package com.intuit.benten.hackernews.utils;

import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.hackernews.model.HackernewsItem;

import java.util.List;

/**
 * Created by jleveroni on 10/09/2019
 */
public class SlackHackerNewsMessageRenderer {
    public static BentenSlackResponse renderItemList(List<HackernewsItem> stories) {
        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();
        slackFormatter.text("Here's what I was able to dig up:").newline();

        for (HackernewsItem story : stories) {
            slackFormatter.bold(story.getTitle())
                .italic(" | By: " + story.getBy() + " | ")
                .link(story.getUrl(), "View article")
                .newline();
        }

        bentenSlackResponse.setSlackText(slackFormatter.build());
        return bentenSlackResponse;
    }

    public static BentenSlackResponse renderItem(HackernewsItem story) {
        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();

        bentenSlackResponse.setSlackText(slackFormatter
                .text("Here's what I was able to dig up:").newline()
                .bold(story.getTitle()).newline()
                .text(story.getText()).newline()
                .italic(" | By: ").italic(story.getBy())
                .italic(" | Score: ").italic(story.getScore().toString()).newline()
                .link(story.getUrl(), "View article").build());

        return bentenSlackResponse;
    }
}
