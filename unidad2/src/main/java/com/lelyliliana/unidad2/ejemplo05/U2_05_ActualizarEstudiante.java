package com.lelyliliana.unidad2.ejemplo05;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U2_05_ActualizarEstudiante {

    private final EstudianteRepository estudianteRepository;

    public U2_05_ActualizarEstudiante(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @PutMapping("/estudiantes-db/{id}")
    public ResponseEntity<Estudiante> actualizar(
            @PathVariable Long id,
            @RequestBody Estudiante datosActualizados) {

        return estudianteRepository.findById(id)
                .map(estudiante -> {

                    estudiante.setNombre(datosActualizados.getNombre());
                    estudiante.setPrograma(datosActualizados.getPrograma());

                    Estudiante actualizado =
                            estudianteRepository.save(estudiante);

                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}