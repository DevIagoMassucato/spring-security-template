package com.iagomassucato.springsecuritytemplate.anime;

import jakarta.validation.constraints.NotBlank;

public record AnimeRequest(
        @NotBlank(message = "title is required")
        String title
) {
}
