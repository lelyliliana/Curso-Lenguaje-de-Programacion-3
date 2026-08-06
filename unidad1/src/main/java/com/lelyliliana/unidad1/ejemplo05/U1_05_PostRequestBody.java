package com.lelyliliana.unidad1.ejemplo05;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class U1_05_PostRequestBody {

    @PostMapping("/estudiantes")
    public String crearEstudiante(@RequestBody Map<String, String> datos) {
        String nombre = datos.get("nombre");
        String programa = datos.get("programa");

        return "Estudiante recibido: " + nombre + " - " + programa;
    }
}