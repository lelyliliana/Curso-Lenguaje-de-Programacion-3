package com.lelyliliana.unidad2.ejemplo09;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U2_09_ConsumirApiExterna {

    private final GitHubService gitHubService;

    public U2_09_ConsumirApiExterna(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/github/{usuario}")
    public GitHubUsuarioResponse consultarUsuario(
            @PathVariable String usuario) {

        return gitHubService.buscarUsuario(usuario);
    }
}