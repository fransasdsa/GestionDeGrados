// src/main/java/com/universidad/advisor/AdvisorServiceApplication.java
package com.universidad.advisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
public class AdvisorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvisorServiceApplication.class, args);
    }
}
