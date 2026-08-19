package com.lelyliliana.unidad2.ejemplo07;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class U2_07_BuscarPorPrograma {

    private final EstudianteRepository estudianteRepository;

    public U2_07_BuscarPorPrograma(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @GetMapping("/estudiantes-db/buscar")
    public List<Estudiante> buscarPorPrograma(
            @RequestParam String programa) {

        return estudianteRepository.findByPrograma(programa);
    }
}