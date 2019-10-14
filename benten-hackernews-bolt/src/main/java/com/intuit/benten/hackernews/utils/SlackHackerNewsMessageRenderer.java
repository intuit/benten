package com.intuit.benten.hackernews.utils;

import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.hackernews.exceptions.BentenHackernewsException;
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
                .italic("| By:").italic(story.getBy()).italic("|").text(" ");

            if (story.getUrl() == null) {
                slackFormatter.link(
                        HackernewsConstants.ApiEndpoints.HACKERNEWS_ITEM_URL + story.getId(),
                        "View article").newline();
            } else {
                slackFormatter.link(story.getUrl(), "View article").newline();
            }
        }

        bentenSlackResponse.setSlackText(slackFormatter.build());
        return bentenSlackResponse;
    }

    public static BentenSlackResponse renderItem(HackernewsItem story) {
        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();

        slackFormatter.text("Here's what I was able to dig up:").newline()
                .bold(story.getTitle()).newline()
                .text(story.getText()).newline()
                .italic("| By:").italic(story.getBy())
                .italic("| Score:").italic(story.getScore().toString()).newline();

        if (story.getUrl() == null) {
            slackFormatter.link(
                    HackernewsConstants.ApiEndpoints.HACKERNEWS_ITEM_URL + story.getId(),
                    "View article").newline();
        } else {
            slackFormatter.link(story.getUrl(), "View article").newline();
        }

        bentenSlackResponse.setSlackText(slackFormatter.build());
        return bentenSlackResponse;
    }

    public static BentenSlackResponse renderError(String action, BentenHackernewsException e) {
        BentenSlackResponse bentenSlackResponse= new BentenSlackResponse();
        SlackFormatter slackFormatter = SlackFormatter.create();

        slackFormatter.bold("An error occurred processing the ").text(action).text("action");
        slackFormatter.text("Error description: ").text(e.getMessage());
        bentenSlackResponse.setSlackText(slackFormatter.build());
        return bentenSlackResponse;
    }
}
