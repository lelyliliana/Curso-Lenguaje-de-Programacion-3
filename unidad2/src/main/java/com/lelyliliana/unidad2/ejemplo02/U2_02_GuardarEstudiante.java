package com.lelyliliana.unidad2.ejemplo02;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U2_02_GuardarEstudiante {

    private final EstudianteRepository estudianteRepository;

    public U2_02_GuardarEstudiante(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @PostMapping("/estudiantes-db")
    public Estudiante guardar(
            @RequestBody Estudiante estudiante) {

        return estudianteRepository.save(estudiante);
    }
}