package com.paglima.monster.configuration.resttemplate.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.nio.reactor.IOReactorExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.AsyncClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.AsyncRestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class AsyncRestTemplateFactory {

    private final List<AsyncClientHttpRequestInterceptor> interceptors;
    private final IOReactorExceptionHandler exceptionHandler;

    @Autowired
    public AsyncRestTemplateFactory(List<AsyncClientHttpRequestInterceptor> interceptors, IOReactorExceptionHandler exceptionHandler) {
        this.interceptors = interceptors;
        this.exceptionHandler = exceptionHandler;
    }

    public AsyncRestTemplate create(int readTimeout,
                                    int connectionTimeout,
                                    int connectionRequestTimeout,
                                    int maxTotalConnections,
                                    int maxConnectionsPerRoute) {


        AsyncClientHttpRequestFactory asyncClientHttpRequestFactory = getAsyncClientHttpRequestFactory(readTimeout,
                connectionTimeout, connectionRequestTimeout, maxTotalConnections, maxConnectionsPerRoute);

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(asyncClientHttpRequestFactory);
        asyncRestTemplate.setInterceptors(interceptors);

        return asyncRestTemplate;
    }

    private AsyncClientHttpRequestFactory getAsyncClientHttpRequestFactory(int readTimeout, int connectionTimeout, int connectionRequestTimeout, int maxTotalConnections, int maxConnectionsPerRoute) {

        CloseableHttpAsyncClient httpAsyncClient = getHttpAsyncClient(readTimeout, connectionTimeout, connectionRequestTimeout, maxTotalConnections, maxConnectionsPerRoute);

        HttpComponentsAsyncClientHttpRequestFactory httpRequestFactory = new HttpComponentsAsyncClientHttpRequestFactory();
        httpRequestFactory.setHttpAsyncClient(httpAsyncClient);
        httpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
        httpRequestFactory.setConnectTimeout(connectionTimeout);
        httpRequestFactory.setReadTimeout(readTimeout);
        httpRequestFactory.setBufferRequestBody(true);

        return httpRequestFactory;
    }

    private CloseableHttpAsyncClient getHttpAsyncClient(int readTimeout,
                                                        int connectionTimeout,
                                                        int connectionRequestTimeout,
                                                        int maxTotalConnections,
                                                        int maxConnectionsPerRoute) {

        try {

            RequestConfig config = getRequestConfig(readTimeout, connectionTimeout, connectionRequestTimeout);

            IOReactorConfig ioReactorConfig = getIoReactorConfig(readTimeout, connectionTimeout);

            PoolingNHttpClientConnectionManager connectionManager = getPoolingNHttpClientConnectionManager(connectionTimeout, maxTotalConnections, maxConnectionsPerRoute, ioReactorConfig);

            return HttpAsyncClientBuilder.create()
                    .setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(config)
                    .setDefaultIOReactorConfig(ioReactorConfig)
                    .setMaxConnPerRoute(maxConnectionsPerRoute)
                    .setMaxConnTotal(maxTotalConnections)
                    .build();

        } catch (Exception exc) {
            throw new RuntimeException(exc.getMessage(), exc);
        }
    }

    private IOReactorConfig getIoReactorConfig(int readTimeout, int connectionTimeout) {
        return IOReactorConfig.custom()
                        .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                        .setConnectTimeout(connectionTimeout)
                        .setSoTimeout(readTimeout)
                        .build();
    }

    private PoolingNHttpClientConnectionManager getPoolingNHttpClientConnectionManager(int connectionTimeout, int maxTotalConnections, int maxConnectionsPerRoute, IOReactorConfig ioReactorConfig) throws IOException {

        DefaultConnectingIOReactor ioReactor = getDefaultConnectingIOReactor(ioReactorConfig);

        PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connectionManager.setMaxTotal(maxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);
        connectionManager.closeIdleConnections(connectionTimeout, TimeUnit.MILLISECONDS);
        return connectionManager;
    }

    private DefaultConnectingIOReactor getDefaultConnectingIOReactor(IOReactorConfig ioReactorConfig) throws IOReactorException {
        DefaultConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        ioReactor.setExceptionHandler(exceptionHandler);
        //ioReactor.shutdown(30000);
        return ioReactor;
    }

    private RequestConfig getRequestConfig(int readTimeout, int connectionTimeout, int connectionRequestTimeout) {

        return RequestConfig.custom()
                .setSocketTimeout(readTimeout)
                .setConnectTimeout(connectionTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
    }

}
