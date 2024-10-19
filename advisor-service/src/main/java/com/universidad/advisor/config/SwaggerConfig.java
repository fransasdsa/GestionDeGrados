package com.universidad.advisor.config;

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

        return new OpenAPI()
                .info(new Info()
                        .title("Advisor Service API")
                        .description("Servicio de Gestión de Asesores")
                        .version("v1.0"))
                .servers(List.of(server));
    }
}
