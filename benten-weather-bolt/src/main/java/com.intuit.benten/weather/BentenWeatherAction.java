package com.intuit.benten.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.weather.client.BentenWeatherClent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ashpak Shaikh
 * @version 1.0
 */
@Component
@Slf4j
@ActionHandler(action = "action_benten_weather")
public class BentenWeatherAction implements BentenActionHandler {

  @Autowired
  BentenWeatherClent bentenWeatherClent;

  @Override
  public BentenHandlerResponse handle(BentenMessage bentenMessage) {

    String city = BentenMessageHelper.getParameterAsString(bentenMessage, "city");
    BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
    BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();

    try {
      String response = bentenWeatherClent.getWeatherFromCurrentCity(city);
      JsonObject object = (JsonObject) new JsonParser().parse(response);
      JsonArray weather = object.getAsJsonArray("weather");
      SlackFormatter responseToSend = SlackFormatter.create();
      for (JsonElement node : weather) {
        JsonObject asJsonObject = node.getAsJsonObject();
        JsonPrimitive main = asJsonObject.getAsJsonPrimitive("description");
        responseToSend.text(main.getAsString());
      }
      JsonObject main = object.getAsJsonObject("main");
      for (String key : main.keySet()) {
        String value = main.getAsJsonPrimitive(key).getAsString();
        responseToSend.bold(key + ":" + value);
        responseToSend.newline();
      }

      bentenSlackResponse.setSlackText(responseToSend.build());
      bentenMessage.getChannel().sendMessage(bentenHandlerResponse, bentenMessage.getChannelInformation());

    } catch (Exception ex) {
      log.error("Exception caught ", ex);
    }

    bentenSlackResponse.setSlackText("Thank you for asking");
    bentenHandlerResponse.setBentenSlackResponse(bentenSlackResponse);
    return bentenHandlerResponse;

  }
}
