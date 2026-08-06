package com.lelyliliana.unidad1.ejemplo10;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_10_InyeccionDependencias {

    private final SaludoService saludoService;

    public U1_10_InyeccionDependencias(SaludoService saludoService) {
        this.saludoService = saludoService;
    }

    @GetMapping("/saludo-servicio/{nombre}")
    public String saludar(@PathVariable String nombre) {
        return saludoService.generarSaludo(nombre);
    }
}