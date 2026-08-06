package com.lelyliliana.unidad1.ejemplo12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EstudianteServiceTest {

    @Test
    void retornaQueElEstudianteExiste() {

        RepositorioEstudiantes repositorio =
                mock(RepositorioEstudiantes.class);

        when(repositorio.existePorId(1L))
                .thenReturn(true);

        EstudianteService servicio =
                new EstudianteService(repositorio);

        String resultado = servicio.consultarEstado(1L);

        assertEquals("El estudiante existe", resultado);
    }

    @Test
    void retornaQueElEstudianteNoExiste() {

        RepositorioEstudiantes repositorio =
                mock(RepositorioEstudiantes.class);

        when(repositorio.existePorId(2L))
                .thenReturn(false);

        EstudianteService servicio =
                new EstudianteService(repositorio);

        String resultado = servicio.consultarEstado(2L);

        assertEquals("El estudiante no existe", resultado);
    }
}