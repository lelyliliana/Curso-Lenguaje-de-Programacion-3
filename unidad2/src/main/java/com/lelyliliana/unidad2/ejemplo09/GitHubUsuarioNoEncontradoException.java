package com.lelyliliana.unidad2.ejemplo09;

public class GitHubUsuarioNoEncontradoException extends RuntimeException {

    public GitHubUsuarioNoEncontradoException(String usuario) {
        super("No se encontró el usuario de GitHub: " + usuario);
    }
}