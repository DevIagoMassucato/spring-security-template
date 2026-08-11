package com.iagomassucato.spring.security.template.security.me;

import jakarta.validation.constraints.NotBlank;

public record MeRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "password is required")
        String password,
        @NotBlank(message = "currentPassword is required")
        String currentPassword
) {
}
