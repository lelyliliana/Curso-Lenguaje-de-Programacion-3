package com.lelyliliana.unidad3.ejemplo06;

import io.micrometer.tracing.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class U3_06_Trazabilidad {

    private final Tracer tracer;

    public U3_06_Trazabilidad(Tracer tracer) {
        this.tracer = tracer;
    }

    @GetMapping("/traza")
    public Map<String, String> consultarTraza() {

        Map<String, String> respuesta = new LinkedHashMap<>();

        if (tracer.currentSpan() != null) {
            respuesta.put(
                    "traceId",
                    tracer.currentSpan().context().traceId()
            );

            respuesta.put(
                    "spanId",
                    tracer.currentSpan().context().spanId()
            );
        }

        respuesta.put(
                "mensaje",
                "Solicitud procesada con trazabilidad"
        );

        return respuesta;
    }
}