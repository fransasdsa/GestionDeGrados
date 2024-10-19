package com.universidad.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class GatewayConfig {

    @Autowired
    private RouteLocatorBuilder builder;

    @Bean
    public RouteLocator customRouteLocator() {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://auth-service"))
                .route("project-service", r -> r.path("/api/v1/projects/**")
                        .uri("lb://project-service"))
                .route("evaluation-service", r -> r.path("/api/v1/evaluations/**")
                        .uri("lb://evaluation-service"))
                .route("researchline-service", r -> r.path("/api/v1/researchlines/**")
                        .uri("lb://researchline-service"))
                .build();
    }
}