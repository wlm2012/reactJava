package com.study.reactJava.common.config;


import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {


    private static final int HTTP_MAX_IDLE = 100;
    private static final int HTTP_KEEP_ALIVE = 20;
    private static final int HTTP_TIMEOUT_MILLISECONDS = 60_000;

    private static final int HTTP_TIMEOUT =60;
    private static final int HTTP_CONNECTION_TIMEOUT = 30;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(HTTP_TIMEOUT_MILLISECONDS);
        requestFactory.setConnectTimeout(HTTP_TIMEOUT_MILLISECONDS);
        return new RestTemplate(requestFactory);
    }

/*    @Bean
    public RestTemplate okhttpRestTemplate() {
        ConnectionPool pool = new ConnectionPool(HTTP_MAX_IDLE, HTTP_KEEP_ALIVE, TimeUnit.SECONDS);
        OkHttpClient builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectionPool(pool)
                .connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory(builder));
    }*/

    @Bean
    public RestTemplate httpRestTemplate() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(HTTP_MAX_IDLE);
        connectionManager.setDefaultMaxPerRoute(HTTP_MAX_IDLE);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(Timeout.of(HTTP_TIMEOUT, TimeUnit.SECONDS)) // timeout to get connection from pool
                .setResponseTimeout(Timeout.of(HTTP_TIMEOUT, TimeUnit.SECONDS))
                .build();

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }
}