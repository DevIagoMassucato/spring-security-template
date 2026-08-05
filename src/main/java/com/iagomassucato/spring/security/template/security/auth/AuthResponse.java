package com.iagomassucato.spring.security.template.security.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthResponse {
    private final String accessToken;
    private final String refreshToken;
}
