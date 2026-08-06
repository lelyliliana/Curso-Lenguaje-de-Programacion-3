package com.lelyliliana.unidad1.ejemplo02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_02_GetBasico {

    @GetMapping("/saludo")
    public String saludar() {
        return "Hola desde Lenguaje de Programación III";
    }
}