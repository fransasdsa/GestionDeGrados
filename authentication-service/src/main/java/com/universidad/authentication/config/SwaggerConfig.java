package com.universidad.authentication.config;

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
        Server server = new Server();
        server.setUrl("/api/v1");
        server.setDescription("Servidor para la versión 1 de la API");

        return new OpenAPI()
                .info(new Info().title("Authentication Service API")
                        .description("Servicio de Autenticación y Autorización")
                        .version("v1.0"))
                .servers(List.of(server));
    }
}
