package com.intuit.benten.hackernews.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.hackernews.utils.HackernewsConstants;
import org.springframework.stereotype.Component;

/**
 * Created by jleveroni on 10/09/2019
 */
@Component
@ActionHandler(action = HackernewsConstants.HackernewsActions.ACTION_HACKERNEWS_GET_NEW_STORIES)
public class HackernewsGetNewStoriesAction extends BaseHackernewsGetCollectionAction implements BentenActionHandler {

}