package com.intuit.benten.splunk;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class Expectations {
    private static final String appId = "83A3E4B8-0616-4B64-8C55-C34EFDA351C9";
    private static Gson gson = new Gson();

    public static void createDefaultExpectations(ClientAndServer mockServer) {
        // GET
        getAppId(mockServer);
    }

    private static void getAppId(ClientAndServer mockServer) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("app_id", appId);
        mockServer.when(request().withMethod("GET")
                .withPath("/get_token")
                .withQueryStringParameter("auth_code", "1234"))
                .respond(response().withStatusCode(200).withBody(gson.toJson(jsonObject)));
    }

}
