package com.intuit.benten.weather.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties("benten.weather")
@PropertySource("classpath:benten.properties")
@Data
public class WeatherProperties {
  String baseUrl;
  String token;
}
