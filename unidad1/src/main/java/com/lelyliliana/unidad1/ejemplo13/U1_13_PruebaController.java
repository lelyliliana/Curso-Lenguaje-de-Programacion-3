package com.lelyliliana.unidad1.ejemplo13;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_13_PruebaController {

    @GetMapping("/estado-api")
    public String consultarEstado() {
        return "API funcionando correctamente";
    }
}