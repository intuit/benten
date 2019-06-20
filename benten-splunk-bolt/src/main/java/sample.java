import com.intuit.benten.splunk.util.BasicTimeDuration;
import com.intuit.benten.splunk.util.BasicTimeDurationHelper;
import com.splunk.*;

import java.io.InputStream;
import java.util.HashMap;

public class sample {
    public static void main(String a[]){
        ServiceArgs loginArgs = new ServiceArgs();
        loginArgs.setUsername("******");
        loginArgs.setPassword("******");
        loginArgs.setHost("*******");
        loginArgs.setPort(8089);
        loginArgs.setScheme("https");

        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);

        Service splunkService = Service.connect(loginArgs);
        String searchQuery_normal = "search index=cls_prod_app applicationid=\"*\" appname=\"lacerte\" applicationversion=\"2018\" message=\"*\"";

        BasicTimeDuration d = new BasicTimeDuration();
        Args oneShotSearch = new Args();
        oneShotSearch.put("earliest_time", BasicTimeDurationHelper.timeDurationForSearch(d));
        oneShotSearch.put("latest_time", "now");
        oneShotSearch.put("output_mode", "json");

        InputStream oneShotSearchResults = splunkService.oneshotSearch(searchQuery_normal, oneShotSearch);

        try{
            ResultsReaderXml resultsReader = new ResultsReaderXml(oneShotSearchResults);
            System.out.println("Searching everything in a  1-hour time range starting now and displaying 10 results in XML:\n");
            HashMap<String, String> event;
            while ((event = resultsReader.getNextEvent()) != null) {
                System.out.println("\n********EVENT********");
                for (String key: event.keySet())
                    System.out.println("   " + key + ":  " + event.get(key));
            }
            resultsReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
