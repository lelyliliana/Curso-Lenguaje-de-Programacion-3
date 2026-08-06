package com.lelyliliana.unidad1.ejemplo10;

import org.springframework.stereotype.Service;

@Service
public class SaludoService {

    public String generarSaludo(String nombre) {
        return "Hola, " + nombre + ". Bienvenido a Lenguaje de Programación III";
    }
}