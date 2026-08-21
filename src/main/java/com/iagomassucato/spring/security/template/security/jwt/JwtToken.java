package com.iagomassucato.spring.security.template.security.jwt;

import java.time.Instant;

public record JwtToken (
        String tokenId,
        String token,
        Instant expirationDate
) {
}
