package com.lelyliliana.unidad3.ejemplo04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U3_04_Logging {

    private static final Logger logger =
            LoggerFactory.getLogger(U3_04_Logging.class);

    @GetMapping("/procesar")
    public String procesar(
            @RequestParam(defaultValue = "normal") String tipo) {

        logger.info("Se recibió una solicitud con tipo: {}", tipo);

        if ("advertencia".equalsIgnoreCase(tipo)) {
            logger.warn("La solicitud fue marcada como advertencia");
        }

        if ("error".equalsIgnoreCase(tipo)) {
            logger.error("Se simuló un error para fines académicos");
        }

        return "Solicitud procesada: " + tipo;
    }
}