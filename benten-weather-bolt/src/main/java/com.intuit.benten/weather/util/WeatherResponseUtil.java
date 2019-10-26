package com.intuit.benten.weather.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.formatters.SlackFormatter;

public class WeatherResponseUtil {

  public static final String TEMPERATURE_FORMAT = "The temperature is %s Kelvin";
  public static final String PRESSURE_FORMAT = "Atmospheric pressure is %s hPa";
  public static final String HUMIDITY_FORMAT = "Humidity is %s%%";
  public static final String WIND_FORMAT = "Wind speed is %s meters/seconds";
  public static final String CLOUD_FORMAT = "Cloudiness is %s%%";
  public static final String RAIN_FORMAT = "%s mm rain in the last 1 hour";
  public static final String SNOW_FORMAT = "%s mm snow in the last 1 hour";
  public static String EERROR_MESSAGE = "Weather details not available at this moment. Please try again later";

  public static String format(String response) {

    SlackFormatter responseToSend = SlackFormatter.create();

    if (response == null || response.isEmpty()) {
      return getErrorMessage(responseToSend);
    }

    //Parse error
    JsonObject object = null;
    try {
       object = (JsonObject) new JsonParser().parse(response);
    }
    catch (Exception e) {
      return getErrorMessage(responseToSend);
    }
    //Invalid code error
    JsonPrimitive code = object.getAsJsonPrimitive("cod");
    if (code == null || code.getAsInt()!= 200) {
      return getErrorMessage(responseToSend);
    }

    //Weather descriptions
    JsonArray weather = object.getAsJsonArray("weather");
    if(weather != null){
      for (JsonElement node : weather) {
        JsonObject asJsonObject = node.getAsJsonObject();
        JsonPrimitive description = asJsonObject.getAsJsonPrimitive("description");
        responseToSend.preformatted("Weather shows " + description.getAsString());
        responseToSend.newline();
      }
    }

    formatMessage(responseToSend, object, "main", "temp", TEMPERATURE_FORMAT, false);
    formatMessage(responseToSend, object, "main", "pressure", PRESSURE_FORMAT, false);
    formatMessage(responseToSend, object, "main", "humidity", HUMIDITY_FORMAT, false);
    formatMessage(responseToSend, object, "wind", "speed", WIND_FORMAT, true);
    formatMessage(responseToSend, object, "clouds", "all", CLOUD_FORMAT, true);
    formatMessage(responseToSend, object, "snow", "1h", SNOW_FORMAT, true);
    formatMessage(responseToSend, object, "rain", "1h", RAIN_FORMAT, true);
    return responseToSend.build();
  }

  private static void formatMessage(SlackFormatter responseToSend, JsonObject object, String checker, String key,
      String messageFormat, boolean elseMessage) {
    JsonObject checkerObject = object.getAsJsonObject(checker);
    if (checkerObject != null && checkerObject.isJsonObject() && checkerObject.has(key)) {
      String value = checkerObject.getAsJsonPrimitive(key).getAsString();
      responseToSend.bold(String.format(messageFormat, value));
      responseToSend.newline();
    } else if(elseMessage){
      responseToSend.bold("No " + checker);
      responseToSend.newline();
    }
  }

  private static String getErrorMessage(SlackFormatter responseToSend) {
    responseToSend.preformatted(EERROR_MESSAGE);
    responseToSend.newline();
    return responseToSend.build();
  }
}

