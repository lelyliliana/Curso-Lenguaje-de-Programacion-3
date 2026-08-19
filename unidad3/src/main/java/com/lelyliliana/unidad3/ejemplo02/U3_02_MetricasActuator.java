package com.lelyliliana.unidad3.ejemplo02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U3_02_MetricasActuator {

    @GetMapping("/mensaje")
    public String mensaje() {
        return "Endpoint utilizado para generar métricas";
    }
}