package com.iagomassucato.spring.security.template.anime;

import jakarta.validation.constraints.NotBlank;

public record AnimeRequest(
        @NotBlank(message = "title is required")
        String title
) {
}
