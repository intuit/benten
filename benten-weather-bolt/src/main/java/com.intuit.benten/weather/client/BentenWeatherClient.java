package com.intuit.benten.weather.client;

import com.intuit.benten.common.http.HttpHelper;
import com.intuit.benten.weather.properties.WeatherProperties;
import java.io.IOException;
import java.net.URLEncoder;
import javax.annotation.PostConstruct;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BentenWeatherClient {

  @Autowired
  private WeatherProperties weatherProperties;

  @Autowired
  private HttpHelper httpHelper;

  private String url;

  @PostConstruct
  public void setup() {
    url = weatherProperties.getBaseUrl() + weatherProperties.getToken();
  }

  public String getWeatherFromCurrentCity(String city) throws IOException {
    HttpGet httpGet = new HttpGet(url + "&q=" + URLEncoder.encode(city,"UTF-8"));
    HttpResponse httpResponse = httpHelper.getClient().execute(httpGet);
    return EntityUtils.toString(httpResponse.getEntity());
  }


}
