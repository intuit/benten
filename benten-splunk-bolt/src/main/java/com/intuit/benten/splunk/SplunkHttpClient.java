package com.intuit.benten.splunk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.benten.splunk.properties.SplunkProperties;
import com.intuit.benten.splunk.util.BasicTimeDuration;
import com.intuit.benten.splunk.util.BasicTimeDurationHelper;
import com.splunk.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class SplunkHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(SplunkHttpClient.class);

    @Autowired
    private SplunkProperties splunkProperties;

    @PostConstruct
    public void init() {
        try {
            //authenticate();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private Service splunkService;

    private void authenticate() {
        ServiceArgs loginArgs = new ServiceArgs();
        loginArgs.setUsername(splunkProperties.getUsername());
        loginArgs.setPassword(splunkProperties.getPassword());
        loginArgs.setHost("oprdptghd302");
        loginArgs.setPort(8089);
        loginArgs.setScheme("https");

        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);

        splunkService = Service.connect(loginArgs);
    }


    public ArrayList<HashMap<String, String>> runQuery(String applicationId) {
        authenticate();
        //Debug message
        System.out.println("The application id is " + applicationId);

        String oneShotSearchQuery = "search index=cls_prod_app applicationid=\"" + applicationId + "\" appname=\"lacerte\" applicationversion=\"2018\" message=\"*\"";

        HashMap<String, String> event;
        ArrayList<HashMap<String, String>> listOfTransactions = new ArrayList<>();

        //Build arguments for one shot search
        BasicTimeDuration d = new BasicTimeDuration();
        Args oneShotSearchArgs = new Args();
        oneShotSearchArgs.put("earliest_time", BasicTimeDurationHelper.timeDurationForSearch(d));
        oneShotSearchArgs.put("latest_time", "now");
        oneShotSearchArgs.put("output_mode", "json");

        InputStream oneShotSearchResults = splunkService.oneshotSearch(oneShotSearchQuery, oneShotSearchArgs);
        ResultsReaderJson resultsReaderNormalSearch;
        try {
            resultsReaderNormalSearch = new ResultsReaderJson(oneShotSearchResults);

            while ((event = resultsReaderNormalSearch.getNextEvent()) != null) {
                System.out.println("\n****************EVENT****************\n");

                String userInfo = event.get("_raw");
                userInfo = improveMalformedJson(userInfo);

                ObjectMapper mapper = new ObjectMapper();
                HashMap<String, String> userData;
                try {
                    userData = mapper.readValue(userInfo, HashMap.class);
                    if (userData != null) {
                        listOfTransactions.add(userData);

                        //debug message
                        System.out.println(userData.get("appname"));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfTransactions;
    }

    private String improveMalformedJson(String userInfo) {
        userInfo = userInfo.substring(userInfo.indexOf(" appname") + 1);
        userInfo = userInfo.trim();
        userInfo = userInfo.replaceAll("\"=", "\"")
                .replaceAll("=", "\":")
                .replaceAll("\" ", "\", \"")
                .replaceAll("\"token\":", "\"token\":\"")
                .replaceAll(" tid\"", "\", \"tid\"")
                .replaceAll("\\n", "");
        userInfo = "{\"" + userInfo + "}";
        return userInfo;
    }
}