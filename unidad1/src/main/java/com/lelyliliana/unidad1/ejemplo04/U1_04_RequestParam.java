package com.lelyliliana.unidad1.ejemplo04;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_04_RequestParam {

    @GetMapping("/bienvenida")
    public String bienvenida(@RequestParam String nombre) {
        return "Bienvenida, " + nombre;
    }
}