package com.lelyliliana.unidad2.ejemplo09;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubUsuarioResponse(

        String login,

        String name,

        @JsonProperty("public_repos")
        int publicRepos,

        int followers,

        @JsonProperty("html_url")
        String htmlUrl

) {
}