package com.lelyliliana.unidad3.ejemplo03;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U3_03_MetricaPersonalizada {

    private final Counter contadorConsultas;

    public U3_03_MetricaPersonalizada(MeterRegistry meterRegistry) {
        this.contadorConsultas = Counter.builder("curso.consultas")
                .description("Cantidad de consultas realizadas al endpoint")
                .register(meterRegistry);
    }

    @GetMapping("/consulta-contada")
    public String consultar() {

        contadorConsultas.increment();

        return "Consulta registrada";
    }
}