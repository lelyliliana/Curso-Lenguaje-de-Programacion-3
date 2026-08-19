package com.lelyliliana.unidad2.ejemplo09;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class GitHubService {

    private final RestClient restClient;

    public GitHubService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.github.com")
                .build();
    }

    public GitHubUsuarioResponse buscarUsuario(String usuario) {

        try {
            return restClient.get()
                    .uri("/users/{usuario}", usuario)
                    .retrieve()
                    .body(GitHubUsuarioResponse.class);

        } catch (HttpClientErrorException.NotFound ex) {
            throw new GitHubUsuarioNoEncontradoException(usuario);
        }
    }
}