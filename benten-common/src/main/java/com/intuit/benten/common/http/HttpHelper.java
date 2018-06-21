package com.intuit.benten.common.http;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class HttpHelper {

    @Autowired(required = false)
    @Value("${http.maxTotalConnections:1000}")
    private Integer maxTotalConnections;

    @Autowired(required = false)
    @Value("${http.maxConnectionsPerRoute:500}")
    private Integer maxConnectionsPerRoute;

    @Autowired(required = false)
    @Value("${benten.proxy.host:localhost}")
    private String proxyHost;

    @Autowired(required = false)
    @Value("${benten.proxy.port:80}")
    private int proxyPort;

    @Autowired(required = false)
    @Value("${http.keepAliveDurationMilliseconds:120000}")
    private int keepAliveDurationMilliseconds;

    @Autowired(required = false)
    @Value("${http.reapInterval:120000}")
    private long reapInterval;

    @Autowired(required = false)
    @Value("${http.connTimeoutMilliseconds:120000}")
    private int connTimeoutMilliseconds;

    @Autowired(required = false)
    @Value("${http.connReqTimeoutMilliseconds:120000}")
    private int connReqTimeoutMilliseconds;

    @Autowired(required = false)
    @Value("${http.socketTimeoutMilliseconds:120000}")
    private int socketTimeoutMilliseconds;

    @Autowired(required = false)
    @Value("${http.idleConnectionsTimeout:180000}")
    private long idleConnectionsTimeout;

    @Autowired(required = false)
    @Value("${benten.proxy.proxyEnabled:false}")
    private boolean isProxyNeeded;

    @Autowired(required = false)
    @Value("${http.maxRedirects:5}")
    private int maxRedirects;

    private IdleConnectionMonitorThread idcm;


    protected static final Logger LOGGER = Logger
            .getLogger(HttpHelper.class);

    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    private static CloseableHttpClient httpClient;

    @PostConstruct
    public void init() {

        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(maxTotalConnections);
        poolingHttpClientConnectionManager
                .setDefaultMaxPerRoute(this.maxConnectionsPerRoute);

        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response,
                                             HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }
                return keepAliveDurationMilliseconds;
            }

        };
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (isProxyNeeded) {
            requestConfigBuilder.setProxy(new HttpHost(proxyHost, proxyPort));
        }
        RequestConfig requestConfig = requestConfigBuilder
                .setConnectionRequestTimeout(this.connReqTimeoutMilliseconds)
                .setConnectTimeout(connTimeoutMilliseconds)
                .setSocketTimeout(this.socketTimeoutMilliseconds)
                .setStaleConnectionCheckEnabled(false)
                .setRedirectsEnabled(true).setMaxRedirects(maxRedirects)
                .build();
        HttpClientBuilder builder = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig);

        httpClient = builder
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setKeepAliveStrategy(myStrategy).build();
        if (reapInterval > 0 && idleConnectionsTimeout > 0) {
            LOGGER.debug(String
                    .format("Initializing idle connection monitor thread with reap interval %s ms and idle connection time out %s ms",
                            reapInterval, idleConnectionsTimeout));
            idcm = new IdleConnectionMonitorThread(
                    poolingHttpClientConnectionManager, reapInterval,
                    idleConnectionsTimeout);
            idcm.start();
        }
    }

    public CloseableHttpClient getClient(){
        return httpClient;
    }

}
