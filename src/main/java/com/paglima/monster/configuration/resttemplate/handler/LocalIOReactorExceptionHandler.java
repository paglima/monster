package com.paglima.monster.configuration.resttemplate.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.nio.reactor.IOReactorExceptionHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LocalIOReactorExceptionHandler implements IOReactorExceptionHandler {

    @Override
    public boolean handle(IOException ex) {
        log.error("Fatal I/O error", ex);
        return false;
    }

    @Override
    public boolean handle(RuntimeException ex) {
        log.error("Fatal runtime error", ex);
        return false;
    }
}
