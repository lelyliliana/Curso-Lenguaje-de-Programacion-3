package com.lelyliliana.unidad1.ejemplo03;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_03_PathVariable {

    @GetMapping("/saludo/{nombre}")
    public String saludarPorNombre(@PathVariable String nombre) {
        return "Hola, " + nombre;
    }
}