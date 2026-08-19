package com.lelyliliana.unidad2.ejemplo08;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relaciones")
public class U2_08_RelacionManyToOne {

    private final ProgramaRepository programaRepository;
    private final EstudianteConProgramaRepository estudianteRepository;

    public U2_08_RelacionManyToOne(
            ProgramaRepository programaRepository,
            EstudianteConProgramaRepository estudianteRepository) {

        this.programaRepository = programaRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @PostMapping("/programas")
    public Programa crearPrograma(@RequestBody Programa programa) {
        return programaRepository.save(programa);
    }

    @PostMapping("/programas/{programaId}/estudiantes")
    public ResponseEntity<EstudianteConPrograma> crearEstudiante(
            @PathVariable Long programaId,
            @RequestBody EstudianteConPrograma estudiante) {

        return programaRepository.findById(programaId)
                .map(programa -> {

                    estudiante.setPrograma(programa);

                    EstudianteConPrograma guardado =
                            estudianteRepository.save(estudiante);

                    return ResponseEntity.ok(guardado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}