package com.lelyliliana.unidad2.ejemplo01;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstudianteRepository
        extends JpaRepository<Estudiante, Long> {

    List<Estudiante> findByPrograma(String programa);
}