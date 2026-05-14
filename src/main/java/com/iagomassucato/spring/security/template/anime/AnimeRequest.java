package com.iagomassucato.spring.security.template.anime;

import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Getter
public class AnimeRequest {
    @NotBlank(message = "title is required")
    private String title;
}
