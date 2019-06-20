package com.intuit.benten.splunk.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.splunk.exceptions.BentenSplunkException;
import com.intuit.benten.splunk.SplunkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
@ActionHandler(action = SplunkActions.ACTION_GET_USER_INFO)
public class SplunkUserLogsActionHandler implements BentenActionHandler {
    private static final Logger logger = LoggerFactory.getLogger(SplunkUserLogsActionHandler.class);

    @Autowired
    SplunkHttpClient splunkHttpClient = new SplunkHttpClient();

    @Override
    public BentenHandlerResponse handle(BentenMessage bentenMessage) {

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();

        String applicationId = BentenMessageHelper.getParameterAsString(bentenMessage, SplunkActionParameters.PARAMETER_APPLICATION_ID);

        //        try {
//            splunkHttpClient.setApplicationId(applicationId);
//            bentenHandlerResponse.setSlackText("Working on results..");
//        } catch (BentenSplunkException exception) {
//            logger.error(exception.getMessage());
//            bentenHandlerResponse.setSlackText(exception.getMessage());
//
//        } catch (Exception exception) {
//            logger.error(exception.getMessage());
//            throw new BentenSplunkException(exception.getMessage());
//        }
        try {
            ArrayList<HashMap<String, String>> listOfTransactions = splunkHttpClient.runQuery(applicationId);
            buildSlackResponse(bentenSlackResponse, listOfTransactions);
            bentenHandlerResponse.setBentenSlackResponse(bentenSlackResponse);
        } catch (BentenSplunkException exception) {
            logger.error(exception.getMessage());
            bentenHandlerResponse.setSlackText(exception.getMessage());
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            throw new BentenSplunkException(exception.getMessage());
        } finally {
            return bentenHandlerResponse;
        }
    }

    private void buildSlackResponse(BentenSlackResponse bentenSlackResponse, ArrayList<HashMap<String, String>> listOfTransactions) {
        StringBuilder transactionsIds = new StringBuilder();
        for (HashMap<String, String> event : listOfTransactions) {
            transactionsIds
                    .append(event.get("tid"))
                    .append("\n");
        }
        SlackFormatter slackFormatter = SlackFormatter.create();

        bentenSlackResponse.setSlackText(slackFormatter
                .text("The available transaction id's are: \n")
                .text(transactionsIds.toString())
                .build());
    }

}
