package com.lelyliliana.unidad3.ejemplo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lelyliliana.unidad3")
public class U3_01_ActuatorBasico {

    public static void main(String[] args) {
        SpringApplication.run(U3_01_ActuatorBasico.class, args);
    }
}