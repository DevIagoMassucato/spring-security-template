package com.iagomassucato.spring.security.template.security.refreshtoken;

import jakarta.validation.constraints.NotEmpty;

public record RefreshTokenRequest(
        @NotEmpty(message = "refreshToken is required")
        String refreshToken
) {
}
