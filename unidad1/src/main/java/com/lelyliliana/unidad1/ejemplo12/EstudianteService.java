package com.lelyliliana.unidad1.ejemplo12;

public class EstudianteService {

    private final RepositorioEstudiantes repositorioEstudiantes;

    public EstudianteService(RepositorioEstudiantes repositorioEstudiantes) {
        this.repositorioEstudiantes = repositorioEstudiantes;
    }

    public String consultarEstado(Long id) {

        if (repositorioEstudiantes.existePorId(id)) {
            return "El estudiante existe";
        }

        return "El estudiante no existe";
    }
}