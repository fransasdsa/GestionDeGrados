// src/main/java/com/universidad/researchline/ResearchLineServiceApplication.java
package com.universidad.researchline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ResearchLineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchLineServiceApplication.class, args);
    }
}
