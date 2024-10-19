package com.universidad.authentication.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public Mono<Void> handleEmailAlreadyExists(ServerWebExchange exchange, EmailAlreadyExistsException ex) {
        return buildErrorResponse(exchange, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public Mono<Void> handleInvalidCredentials(ServerWebExchange exchange, InvalidCredentialsException ex) {
        return buildErrorResponse(exchange, HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("message", message);
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(errorAttributes.toString().getBytes()))
        );
    }
}
