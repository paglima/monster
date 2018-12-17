package com.paglima.monster.configuration.resttemplate.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;

@Component
@Slf4j
public class NoCacheInterceptor implements ClientHttpRequestInterceptor, AsyncClientHttpRequestInterceptor {

	private static final String PASSTHRU_DEBUG_MESSAGE = "Cache controle setado para no-cache com sucesso para o endpoint [{} {}]";
    public static final String NO_CACHE = "no-cache";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        intercept(request);

        return execution.execute(request, body);
    }

    @Override
    public ListenableFuture<ClientHttpResponse> intercept(HttpRequest request, byte[] body, AsyncClientHttpRequestExecution execution) throws IOException {

        intercept(request);

        return execution.executeAsync(request, body);
    }

    private void intercept(HttpRequest request) {

        request.getHeaders().setCacheControl(NO_CACHE);
        request.getHeaders().setPragma(NO_CACHE);

        log.debug(PASSTHRU_DEBUG_MESSAGE, request.getMethod(), request.getURI());
    }

}
