package com.lelyliliana.unidad1.ejemplo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lelyliliana.unidad1")
public class U1_01_PrimeraApi {

    public static void main(String[] args) {
        SpringApplication.run(U1_01_PrimeraApi.class, args);
    }
}