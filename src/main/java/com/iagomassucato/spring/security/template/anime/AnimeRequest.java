package com.iagomassucato.spring.security.template.anime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AnimeRequest {
    @NotBlank(message = "title is required")
    private String title;
}
