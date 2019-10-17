package com.intuit.benten.weather.util;

import org.junit.Assert;
import org.junit.Test;

public class WeatherResponseUtilTest {


  @Test
  public void checkWithNullResponse(){
    Assert.assertTrue(WeatherResponseUtil.format(null).contains(WeatherResponseUtil.EERROR_MESSAGE));
    Assert.assertTrue(WeatherResponseUtil.format("").contains(WeatherResponseUtil.EERROR_MESSAGE));
    Assert.assertTrue(WeatherResponseUtil.format("fdjksfdkj").contains(WeatherResponseUtil.EERROR_MESSAGE));

  }

  @Test
  public void check200Response(){
    Assert.assertTrue(WeatherResponseUtil.format("{'cod': 201}").contains(WeatherResponseUtil.EERROR_MESSAGE));
    Assert.assertTrue(WeatherResponseUtil.format("{'code': 201}").contains(WeatherResponseUtil.EERROR_MESSAGE));
    Assert.assertFalse(WeatherResponseUtil.format("{'cod': 200}").contains(WeatherResponseUtil.EERROR_MESSAGE));
  }

  @Test
  public void checkTemperatureResponse(){
    String response = WeatherResponseUtil.format("{'cod':200, 'main': {'temp': '21'}}");
    Assert.assertTrue(response.contains(String.format(WeatherResponseUtil.TEMPERATURE_FORMAT,"21")));
    Assert.assertTrue(response.contains(String.format("No wind")));
    Assert.assertTrue(response.contains(String.format("No clouds")));
    Assert.assertTrue(response.contains(String.format("No snow")));
    Assert.assertTrue(response.contains(String.format("No rain")));
  }

  @Test
  public void checkPressureResponse(){
    String response = WeatherResponseUtil.format("{'cod':200, 'main': {'pressure': '21'}}");
    Assert.assertTrue(response.contains(String.format(WeatherResponseUtil.PRESSURE_FORMAT,"21")));
    Assert.assertTrue(response.contains(String.format("No wind")));
    Assert.assertTrue(response.contains(String.format("No clouds")));
    Assert.assertTrue(response.contains(String.format("No snow")));
    Assert.assertTrue(response.contains(String.format("No rain")));
  }

  @Test
  public void checkHumidityResponse(){
    String response = WeatherResponseUtil.format("{'cod':200, 'main': {'humidity': '21'}}");
    Assert.assertTrue(response.contains(String.format(WeatherResponseUtil.HUMIDITY_FORMAT,"21")));
    Assert.assertTrue(response.contains(String.format("No wind")));
    Assert.assertTrue(response.contains(String.format("No clouds")));
    Assert.assertTrue(response.contains(String.format("No snow")));
    Assert.assertTrue(response.contains(String.format("No rain")));
  }



  @Test
  public void checkWindResponse(){
    String response = WeatherResponseUtil.format("{'cod':200, 'wind': {'speed': '2000'}}");
    Assert.assertTrue(response.contains(String.format(WeatherResponseUtil.WIND_FORMAT, "2000")));
    Assert.assertTrue(response.contains(String.format("No clouds")));
    Assert.assertTrue(response.contains(String.format("No snow")));
    Assert.assertTrue(response.contains(String.format("No rain")));
  }

  @Test
  public void checkCloudResponse(){
    String response = WeatherResponseUtil.format("{'cod':200, 'clouds': {'all': '2000'}}");
    Assert.assertTrue(response.contains(String.format(WeatherResponseUtil.CLOUD_FORMAT, "2000")));
    Assert.assertTrue(response.contains(String.format("No wind")));
    Assert.assertTrue(response.contains(String.format("No snow")));
    Assert.assertTrue(response.contains(String.format("No rain")));
  }

  @Test
  public void checkSnowResponse(){
    String response = WeatherResponseUtil.format("{'cod':200, 'snow': {'1h': '20'}}");
    Assert.assertTrue(response.contains(String.format(WeatherResponseUtil.SNOW_FORMAT, "20")));
    Assert.assertTrue(response.contains(String.format("No clouds")));
    Assert.assertTrue(response.contains(String.format("No wind")));
    Assert.assertTrue(response.contains(String.format("No rain")));
  }

  @Test
  public void checkRainResponse(){
    String response = WeatherResponseUtil.format("{'cod':200, 'rain': {'1h': '2000'}}");
    Assert.assertTrue(response.contains(String.format(WeatherResponseUtil.RAIN_FORMAT, "2000")));
    Assert.assertTrue(response.contains(String.format("No clouds")));
    Assert.assertTrue(response.contains(String.format("No snow")));
    Assert.assertTrue(response.contains(String.format("No wind")));
  }

}
