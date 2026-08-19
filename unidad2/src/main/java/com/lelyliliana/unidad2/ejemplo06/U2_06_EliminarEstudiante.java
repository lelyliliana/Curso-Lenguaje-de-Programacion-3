package com.lelyliliana.unidad2.ejemplo06;

import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U2_06_EliminarEstudiante {

    private final EstudianteRepository estudianteRepository;

    public U2_06_EliminarEstudiante(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @DeleteMapping("/estudiantes-db/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (!estudianteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        estudianteRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}