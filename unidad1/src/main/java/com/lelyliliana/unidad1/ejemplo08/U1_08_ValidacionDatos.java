package com.lelyliliana.unidad1.ejemplo08;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_08_ValidacionDatos {

    @PostMapping("/estudiantes-validacion")
    public ResponseEntity<String> crearEstudiante(
            @Valid @RequestBody EstudianteValidadoRequest estudiante) {

        String mensaje = "Estudiante registrado: "
                + estudiante.nombre()
                + " - "
                + estudiante.correo();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mensaje);
    }
}