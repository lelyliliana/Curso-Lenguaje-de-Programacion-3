package com.lelyliliana.unidad2.ejemplo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.lelyliliana.unidad2")
@EntityScan(basePackages = "com.lelyliliana.unidad2")
@EnableJpaRepositories(basePackages = "com.lelyliliana.unidad2")
public class U2_01_PrimeraEntidadJPA {

    public static void main(String[] args) {
        SpringApplication.run(U2_01_PrimeraEntidadJPA.class, args);
    }
}