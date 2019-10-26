package com.intuit.benten.weather.actionhandlers;


import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.channel.Channel;
import com.intuit.benten.common.channel.ChannelInformation;
import com.intuit.benten.common.nlp.BentenMessage;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@ComponentScan(value = "com.intuit.benten")
public class BentenWeatherActionHandlerTest {

  @Autowired
  BentenWeatherActionHandler bentenWeatherActionHandler;

  @Test
  public void testHandleRRequest() {
    BentenHandlerResponse bentenHandlerResponse = bentenWeatherActionHandler.handle(constructBentenMessage("foo"));
    Assert.assertEquals(bentenHandlerResponse.getBentenSlackResponse().getSlackText(),
        BentenWeatherActionHandler.THANK_YOU_FOR_ASKING);
  }

  public static BentenMessage constructBentenMessage(String city) {
    HashMap<String, JsonElement> parameters = new HashMap<String, JsonElement>();
    BentenMessage bentenMessage = new BentenMessage();
    parameters.put(BentenWeatherActionHandler.CITY, new JsonPrimitive(city));
    bentenMessage.setParameters(parameters);
    bentenMessage.setChannel(new Channel() {
      @Override
      public void sendMessage(final BentenHandlerResponse bentenHandlerResponse,
          final ChannelInformation channelInformation) {
      }
    });
    return bentenMessage;
  }

}
