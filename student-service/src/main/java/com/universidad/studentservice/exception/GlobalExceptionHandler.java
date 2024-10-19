package com.universidad.studentservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        errorAttributes.put("error", ex.getClass().getSimpleName());
        errorAttributes.put("status", status.value());
        errorAttributes.put("timestamp", System.currentTimeMillis());

        if (ex instanceof StudentNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof ServerWebInputException) {
            status = HttpStatus.BAD_REQUEST;
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse()
                            .bufferFactory()
                            .wrap(new ObjectMapper().writeValueAsBytes(errorAttributes))) // Utiliza ObjectMapper para serializar
            );
        } catch (JsonProcessingException e) {
            // Manejo de error en caso de falla al serializar
            return Mono.error(new RuntimeException("Error al procesar el JSON", e));
        }
    }
}
