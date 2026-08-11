package com.iagomassucato.spring.security.template.security.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
