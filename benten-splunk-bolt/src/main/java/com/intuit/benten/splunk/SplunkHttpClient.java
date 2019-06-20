package com.intuit.benten.splunk;

import com.intuit.benten.splunk.properties.SplunkProperties;
import com.splunk.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class SplunkHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(SplunkHttpClient.class);

    @Autowired
    private SplunkProperties splunkProperties;

    @PostConstruct
    public void init(){
        try {
            //authenticate();
        }
        catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
    }

    private Service splunkService;

    private void authenticate() {
        ServiceArgs loginArgs = new ServiceArgs();
        loginArgs.setUsername("******");
        loginArgs.setPassword("******");
        loginArgs.setHost("******");
        loginArgs.setPort(8089);
        loginArgs.setScheme("https");

        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);

        splunkService = Service.connect(loginArgs);

        //String userLogs = getUserLogs(loginArgs, "hello");
    }

//    private String extractTransactionId(String rawData){
//        String identifier = "tid=\"";
//        for (int i=0; i<identifier.length(); i++){
//
//        }
//    return "";
//    }

    public ArrayList<HashMap<String, String>> runQuery(String applicationId) {
        authenticate();
        System.out.println("The application id is "+applicationId);
        String searchQuery_normal = "search index=cls_prod_app applicationid=\""+applicationId+"\" appname=\"lacerte\" applicationversion=\"2018\" message=\"*\"";
        JobArgs jobargs = new JobArgs();
        jobargs.setExecutionMode(JobArgs.ExecutionMode.NORMAL);

        Job job = splunkService.getJobs().create(searchQuery_normal, jobargs);

        while (!job.isDone()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        HashMap<String, String> event;
        HashMap<String, String> userData = new HashMap<>();
        ArrayList<HashMap<String, String>> listOfTransactions = new ArrayList<>();


        Args outputArgs = new Args();
        outputArgs.put("output_mode","json");
// Get the search results and use the built-in XML parser to display them
        InputStream resultsNormalSearch = job.getResults(outputArgs);

        ResultsReaderJson resultsReaderNormalSearch;
        try {
            resultsReaderNormalSearch = new ResultsReaderJson(resultsNormalSearch);

            while ((event = resultsReaderNormalSearch.getNextEvent()) != null) {
                System.out.println("\n****************EVENT****************\n");
                    String userInfo = event.get("_raw");
                    String[] values = userInfo.replace("\"", "").split(" ");
                    for (String value : values) {
                        if (value.contains("=")) {
                            String[] keyValue = value.split("=");
                            if (keyValue.length == 1) {
                                userData.put(keyValue[0], "");
                            }
                            else {
                                userData.put(keyValue[0], keyValue[1]);
                            }
                            System.out.println("key = " + keyValue[0] + " and the value = " + userData.get(keyValue[0]) + '\n');
                        }
                        else {
                            System.out.println("No key value pairs in this field"+'\n');
                        }
                    }
                    listOfTransactions.add(userData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

// Get properties of the completed job
        System.out.println("\nSearch job properties\n---------------------");
        System.out.println("Search job ID:         " + job.getSid());
        System.out.println("The number of events:  " + job.getEventCount());
        System.out.println("The number of results: " + job.getResultCount());
        System.out.println("Search duration:       " + job.getRunDuration() + " seconds");
        System.out.println("This job expires in:   " + job.getTtl() + " seconds");

        return listOfTransactions;

    }


}