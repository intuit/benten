package com.intuit.benten.splunk.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.splunk.SplunkHttpClient;
import com.intuit.benten.splunk.exceptions.BentenSplunkException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@Component
@ActionHandler(action = SplunkActions.ACTION_GET_USER_INFO)
public class SplunkUserLogsActionHandler implements BentenActionHandler {
    private static final Logger logger = LoggerFactory.getLogger(SplunkUserLogsActionHandler.class);
    private static final String GET_URL_FOR_APPLICATION_ID = "http://localhost:8080/get_token?auth_code=";
    private static SlackFormatter slackFormatter = SlackFormatter.create();
    @Autowired
    SplunkHttpClient splunkHttpClient = new SplunkHttpClient();

    @Override
    public BentenHandlerResponse handle(BentenMessage bentenMessage) {

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
        BentenSlackResponse bentenSlackResponse;

        String authCode = BentenMessageHelper.getParameterAsString(bentenMessage, SplunkActionParameters.PARAMETER_AUTHORISATION_CODE);

        String applicationId = null;
        try {
            applicationId = getApplicationId(authCode);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        try {
            ArrayList<HashMap<String, String>> listOfTransactions = splunkHttpClient.runQuery(applicationId);
            Collections.reverse(listOfTransactions);

            System.out.println(listOfTransactions);
            bentenSlackResponse = generateMeaningfulInfo(listOfTransactions);
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

    private BentenSlackResponse generateMeaningfulInfo(ArrayList<HashMap<String, String>> listOfTransactions) {
        for (HashMap<String, String> transaction : listOfTransactions) {
            String message = transaction.get("message");
            String messageInfo = buildMessageHelper(transaction, "message", "Message -");

            switch (message) {
                case "inputdetailinfo": {

                    StringBuilder inputDetailInfo = new StringBuilder();
                    inputDetailInfo
                            .append(messageInfo)
                            .append(buildMessageHelper(transaction, "action", "User performed an action to get the following info -"))
                            .append(buildMessageHelper(transaction, "taxmodule", "for the tax module"));

                    buildSlackResponse(inputDetailInfo);
                }
                break;
                case "forminfo": {

                    StringBuilder formInfo = new StringBuilder();
                    formInfo
                            .append(messageInfo)
                            .append(buildMessageHelper(transaction, "taxformname", "User asked for the info about the following form -"));

                    buildSlackResponse(formInfo);
                }
                break;
                case "diagnosticsinfo": {

                    StringBuilder diagnosticsInfo = new StringBuilder();
                    diagnosticsInfo.append(messageInfo)
                            .append(buildMessageHelper(transaction, "formname", "Concerned formname -"))
                            .append(buildMessageHelper(transaction, "state", "for the state -"))
                            .append(buildMessageHelper(transaction, "information", "Information about possible issues -"))
                            .append(buildMessageHelper(transaction, "critical", "Possible critical issues which need to be fixed -"))
                            .append(buildMessageHelper(transaction, "fatal", "Possible fatal issues which need to be fixed -"))
                            .append(buildMessageHelper(transaction, "efrejection", "possible reasons for e-file rejection -"));

                    buildSlackResponse(diagnosticsInfo);
                }
                break;
                case "menuclickinfo": {

                    StringBuilder menuClickInfo = new StringBuilder();
                    menuClickInfo.append(messageInfo)
                            .append(buildMessageHelper(transaction, "action", "Menu clicked -"))
                            .append(buildMessageHelper(transaction, "taxmodule", "for", "tax module"));

                    buildSlackResponse(menuClickInfo);
                }
                break;
                case "printinfo": {

                    StringBuilder printInfo = new StringBuilder();
                    printInfo.append(messageInfo)
                            .append(buildMessageHelper(transaction, "forms", "Request for printing the following forms was generated -"))
                            .append(buildMessageHelper(transaction, "printtiggeredfrom", "The print command got triggered from -"))
                            .append(buildMessageHelper(transaction, "trigerredfrom", "The print command got triggered from -"));

                    buildSlackResponse(printInfo);
                }
                break;
                case "antivirusinfo": {

                }
                break;
                case "versioninfo": {

                }
                break;
                case "systeminfo": {

                }
                break;
                case "efileinfo": {
                    StringBuilder efileInfo = new StringBuilder();
                    efileInfo.append(messageInfo)
                            .append(buildMessageHelper(transaction, "status", "The status of the e filing is -"))
                            .append(buildMessageHelper(transaction, "taxtype", "for", "tax type"));
                    buildSlackResponse(efileInfo);
                }
                break;
                case "dmsfilesizeinfo": {

                }
                break;
                case "newclientinfo": {

                }
                break;
                case "efwizardinfo": {

                }
                break;
                case "esignatureinfo": {

                }
                break;
                case "alertviewinfo": {

                }
                break;
                case "panelusageinfo": {

                }
                break;
            }
        }
        BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
        bentenSlackResponse.setSlackText(slackFormatter.build());

        return bentenSlackResponse;
    }

    private String getApplicationId(String authCode) throws IOException {
        String url = GET_URL_FOR_APPLICATION_ID + authCode;
        URL obj = new URL(url);
        String applicationId = "";
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                applicationId = jsonObject.getString("app_id");
            } catch (JSONException e) {
                logger.error(e.getMessage());
            }
            logger.debug("Application id : " + applicationId);
        } else {
            logger.error("Status code - " + responseCode);
        }
        return applicationId;
    }

    private String buildMessageHelper(HashMap<String, String> transaction, String key, String additionalText) {
        String message;
        if (transaction.containsKey(key)) {
            message = "*" + additionalText + "*" + " `" + transaction.get(key) + "`\n";
        } else {
            message = "";
        }
        return message;
    }

    private String buildMessageHelper(HashMap<String, String> transaction, String key, String preText, String postText) {
        String message;
        if (transaction.containsKey(key)) {
            message = preText + " `" + transaction.get(key) + "` " + postText + "\n";
        } else {
            message = "";
        }
        return message;
    }

    private void buildSlackResponse(BentenSlackResponse bentenSlackResponse, ArrayList<HashMap<String, String>> listOfTransactions) {
        StringBuilder transactionsIds = new StringBuilder();
        System.out.println("The size of listOfTransactions is " + listOfTransactions.size());
        for (HashMap<String, String> event : listOfTransactions) {
            System.out.println("tid= " + event.get("tid"));
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

    private void buildSlackResponse(StringBuilder response) {
        response.append("\n");
        slackFormatter.text(response.toString());
    }
}
