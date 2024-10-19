// src/main/java/com/universidad/user/config/SwaggerConfig.java
package com.universidad.user.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        Server server = new Server();
        server.setUrl("/api/v1");

        return new OpenAPI()
                .addServersItem(server)
                .info(new Info().title("User Service API")
                        .description("Servicio de Gestión de Usuarios")
                        .version("v1.0"));
    }
}
