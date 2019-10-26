package com.intuit.benten.hackernews.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by jleveroni on 10/09/2019
 */
@Component
@ConfigurationProperties("benten.hackernews")
@PropertySource("classpath:benten.properties")
public class HackernewsProperties {
    private String baseUrl;
    private String apiVersion;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getUrlWithApiVersion() {
        return this.baseUrl + "/" + this.apiVersion;
    }
}
