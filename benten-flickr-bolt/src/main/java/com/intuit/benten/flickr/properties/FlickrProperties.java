package com.intuit.benten.flickr.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by itstc on 2019-10-07
 */
@Component
@ConfigurationProperties("benten.flickr")
@PropertySource("classpath:benten.properties")
public class FlickrProperties {
    public static String apikey;
    public static String secret;

    public FlickrProperties() {

    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
