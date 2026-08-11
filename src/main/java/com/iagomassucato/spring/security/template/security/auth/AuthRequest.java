package com.iagomassucato.spring.security.template.security.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        String password
) {
}
