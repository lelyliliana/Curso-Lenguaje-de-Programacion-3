package com.lelyliliana.unidad2.ejemplo04;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U2_04_BuscarEstudiantePorId {

    private final EstudianteRepository estudianteRepository;

    public U2_04_BuscarEstudiantePorId(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @GetMapping("/estudiantes-db/{id}")
    public ResponseEntity<Estudiante> buscarPorId(
            @PathVariable Long id) {

        return estudianteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}