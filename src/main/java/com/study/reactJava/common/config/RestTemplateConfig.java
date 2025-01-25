package com.study.reactJava.common.config;


import java.net.http.HttpClient;

/**
 * 默认使用 {@link HttpClient} 不需要额外配置restTemplate，除非调整超时时间
 */
//@Configuration
public class RestTemplateConfig {


    private static final int HTTP_MAX_IDLE = 100;
    private static final int HTTP_KEEP_ALIVE = 20;
    private static final int HTTP_TIMEOUT_MILLISECONDS = 60_000;

    private static final int HTTP_TIMEOUT =60;
    private static final int HTTP_CONNECTION_TIMEOUT = 30;

/*    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(HTTP_TIMEOUT_MILLISECONDS);
        requestFactory.setConnectTimeout(HTTP_TIMEOUT_MILLISECONDS);
        return new RestTemplate(requestFactory);
    }*/

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

/*    @Primary
    @Bean("restTemplate")
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
    }*/

}