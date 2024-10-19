// src/main/java/com/universidad/project/exception/GlobalExceptionHandler.java
package com.universidad.project.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("message", ex.getMessage());

        if (ex instanceof ServerWebInputException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof RuntimeException) {
            status = HttpStatus.BAD_REQUEST;
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(errorAttributes.toString().getBytes()))
        );
    }
}
