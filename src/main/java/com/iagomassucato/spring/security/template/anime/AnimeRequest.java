package com.iagomassucato.spring.security.template.anime;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AnimeRequest {
    @NotBlank(message = "title is required")
    private String title;
}
