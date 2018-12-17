package com.paglima.monster.configuration.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class RestTemplateConfiguration {

    public static final String DEBUG_MESSAGE = "Adicionando o interceptor [{}]";

    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 1000;

    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 100;

    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (2 * 1000);
    
    private static final int DEFAULT_CONNECT_TIMEOUT_MILLISECONDS = (2 * 1000);

    @Autowired(required=false)
    private List<ClientHttpRequestInterceptor> interceptors;

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        addInterceptors(restTemplate);
        return restTemplate;
    }

    private void addInterceptors(RestTemplate restTemplate) {
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();

        for (ClientHttpRequestInterceptor interceptor : getInterceptors()) {
            log.debug(DEBUG_MESSAGE, interceptor.getClass());
            interceptors.add(interceptor);
        }
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        httpRequestFactory.setReadTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS);
		return new BufferingClientHttpRequestFactory(httpRequestFactory);
    }

    @Bean
    public CloseableHttpClient httpClient() {
    	PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);

        RequestConfig config = getRequestConfig();

        return HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .build();
    }

    private RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                    .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLISECONDS)
                    .setSocketTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS)
                    .build();
    }

    private List<ClientHttpRequestInterceptor> getInterceptors() {
    	if(this.interceptors==null) {
    		this.interceptors = new ArrayList<>();
    	}
		return interceptors;
	}

}
