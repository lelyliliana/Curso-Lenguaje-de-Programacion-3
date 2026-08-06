package com.lelyliliana.unidad1.ejemplo06;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_06_PostConDTO {

    @PostMapping("/estudiantes-dto")
    public String crearEstudiante(@RequestBody EstudianteRequest estudiante) {
        return "Estudiante recibido: "
                + estudiante.nombre()
                + " - "
                + estudiante.programa();
    }
}