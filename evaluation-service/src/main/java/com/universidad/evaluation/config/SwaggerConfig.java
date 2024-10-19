package com.universidad.evaluation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Evaluation Service API")
                        .description("Servicio de Gestión de Evaluaciones")
                        .version("v1.0"))
                .servers(List.of(new Server().url("/api/v1")));
    }
}

