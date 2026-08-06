package com.lelyliliana.unidad1.ejemplo07;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_07_ResponseEntity {

    @PostMapping("/usuarios")
    public ResponseEntity<String> crearUsuario(
            @RequestBody UsuarioRequest usuario) {

        String mensaje = "Usuario creado: " + usuario.nombre();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mensaje);
    }
}