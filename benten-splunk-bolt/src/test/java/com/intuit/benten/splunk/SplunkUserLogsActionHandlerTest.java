package com.intuit.benten.splunk;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.splunk.actionhandlers.SplunkUserLogsActionHandler;
import com.intuit.benten.splunk.helpers.Expectations;
import com.intuit.benten.splunk.helpers.MessageBuilder;
import com.intuit.benten.splunk.properties.SplunkProperties;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {SplunkUserLogsActionHandler.class, SplunkHttpClient.class, SplunkProperties.class})
public class SplunkUserLogsActionHandlerTest {
    private static final String expectedApplicationId = "83A3E4B8-0616-4B64-8C55-C34EFDA351C9";
    private static ClientAndServer mockServer;
    @Autowired
    private SplunkUserLogsActionHandler splunkUserLogsActionHandler;

    @BeforeClass
    public static void setUpMockServer() {
        mockServer = startClientAndServer(8089);
        Expectations.createDefaultExpectations(mockServer);
    }

    @AfterClass
    public static void detachMockServer() {
        mockServer.stop();
    }

    @Test
    public void testHandleResponse() {
        JsonElement jsonElement = new JsonPrimitive("1234");
        BentenHandlerResponse bentenHandlerResponse = splunkUserLogsActionHandler.handle(MessageBuilder.constructBentenUserLogsMessage(jsonElement));

        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse());
        Assert.assertNotNull(bentenHandlerResponse.getBentenSlackResponse().getSlackText());
    }

    @Test
    public void testGetApplicationId() {
        String authCode = "1234";
        try {
            String applicationId = splunkUserLogsActionHandler.getApplicationId(authCode);
            Assert.assertEquals(applicationId, expectedApplicationId);

            //debug statement
            System.out.println("Application id = " + applicationId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConvertArrayListToJson() {
        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("1a", "first_a");
        hashMap1.put("2a", "second_a");

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("1b", "first_b");
        hashMap2.put("2b", "second_b");

        HashMap<String, String> hashMap3 = new HashMap<>();
        hashMap3.put("1c", "first_c");
        hashMap3.put("2c", "second_c");

        ArrayList<HashMap<String, String>> listOfTransactions = new ArrayList<>();
        listOfTransactions.add(hashMap1);
        listOfTransactions.add(hashMap2);
        listOfTransactions.add(hashMap3);

        JSONArray jsonArray = null;
        try {
            jsonArray = splunkUserLogsActionHandler.convertArrayListToJson(listOfTransactions);

            //debug statement
            System.out.println("JSON Array : " + jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(jsonArray);
        Assert.assertEquals(jsonArray.length(), 3);
    }

    @Test
    public void testBuildMessageHelperPreTextOnly() {
        HashMap<String, String> transaction = new HashMap<>();
        String key = "key";
        String additionalText = "additionalText";
        transaction.put(key, "value");

        String response = splunkUserLogsActionHandler.buildMessageHelper(transaction, key, additionalText);
        String expectedResponse = "*" + additionalText + "*" + " `" + transaction.get(key) + "`\n";

        //debug statements
        System.out.println("Received Response = " + response);
        System.out.println("Expected Response = " + expectedResponse);

        Assert.assertEquals(response, expectedResponse);
    }

    @Test
    public void testBuildMessageHelperPreAndPostText() {
        HashMap<String, String> transaction = new HashMap<>();
        String key = "key";
        String preText = "preText";
        String postText = "postText";

        transaction.put(key, "value");

        String response = splunkUserLogsActionHandler.buildMessageHelper(transaction, key, preText, postText);
        String expectedResponse = preText + " `" + transaction.get(key) + "` " + postText + "\n";

        //debug statements
        System.out.println("Received Response = " + response);
        System.out.println("Expected Response = " + expectedResponse);

        Assert.assertEquals(response, expectedResponse);
    }

    @Test
    public void testGenerateMeaningfulInfo() {
        ArrayList<HashMap<String, String>> listOfTransactions;
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/asingh63/Downloads/work/benten-build/benten/benten-splunk-bolt/src/test/java/com/intuit/benten/splunk/list-of-transactions.json"));
            JsonElement jsonElement = jsonParser.parse(br);

            Type type = new TypeToken<List<HashMap>>() {
            }.getType();
            listOfTransactions = gson.fromJson(jsonElement, type);
            BentenSlackResponse bentenSlackResponse = splunkUserLogsActionHandler.generateMeaningfulInfo(listOfTransactions);

            //debug statements
            System.out.println(bentenSlackResponse.getSlackText());

            Assert.assertNotNull(bentenSlackResponse);
            Assert.assertNotNull(bentenSlackResponse.getSlackText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
