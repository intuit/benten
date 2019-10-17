package com.intuit.benten.weather.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenSlackResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.weather.util.WeatherResponseUtil;
import com.intuit.benten.weather.client.BentenWeatherClient;
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
public class BentenWeatherActionHandler implements BentenActionHandler {

  public static final String CITY = "city";
  public static final String THANK_YOU_FOR_ASKING = "`Thank you for asking`";
  @Autowired
  BentenWeatherClient bentenWeatherClent;

  @Override
  public BentenHandlerResponse handle(BentenMessage bentenMessage) {
    BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();
    BentenSlackResponse bentenSlackResponse = new BentenSlackResponse();
    bentenHandlerResponse.setBentenSlackResponse(bentenSlackResponse);
    try {
      String city = BentenMessageHelper.getParameterAsString(bentenMessage, CITY);
      bentenSlackResponse.setSlackText(WeatherResponseUtil.format(bentenWeatherClent.getWeatherFromCurrentCity(city)));
      bentenMessage.getChannel().sendMessage(bentenHandlerResponse, bentenMessage.getChannelInformation());
    } catch (Exception ex) {
      log.error("Exception caught ", ex);
    }
    bentenSlackResponse.setSlackText(THANK_YOU_FOR_ASKING);
    return bentenHandlerResponse;

  }
}
