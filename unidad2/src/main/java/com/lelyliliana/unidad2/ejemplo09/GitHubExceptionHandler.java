package com.lelyliliana.unidad2.ejemplo09;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GitHubExceptionHandler {

    @ExceptionHandler(GitHubUsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarUsuarioNoEncontrado(
            GitHubUsuarioNoEncontradoException ex) {

        Map<String, String> respuesta = Map.of(
                "error", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(respuesta);
    }
}