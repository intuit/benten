package com.intuit.benten.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ConfigurationProperties("benten.proxy")
@PropertySource(value = "classpath:benten.properties")
public class BentenProxyConfig {

    public BentenProxyConfig(){

    }

    public boolean isProxyEnabled() {
        return isProxyEnabled;
    }

    public void setProxyEnabled(boolean proxyEnabled) {
        isProxyEnabled = proxyEnabled;
    }

    private boolean isProxyEnabled;

    private String host;

    private int port;

    private String protocol;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

}

