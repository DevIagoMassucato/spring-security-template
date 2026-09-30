package com.iagomassucato.springsecuritytemplate.security.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
