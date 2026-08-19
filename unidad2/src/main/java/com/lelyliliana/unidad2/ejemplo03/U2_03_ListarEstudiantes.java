package com.lelyliliana.unidad2.ejemplo03;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class U2_03_ListarEstudiantes {

    private final EstudianteRepository estudianteRepository;

    public U2_03_ListarEstudiantes(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @GetMapping("/estudiantes-db")
    public List<Estudiante> listar() {
        return estudianteRepository.findAll();
    }
}