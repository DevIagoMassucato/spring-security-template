package com.iagomassucato.springsecuritytemplate.security.jwt;

import java.time.Instant;

public record JwtToken (
        String tokenId,
        String token,
        Instant issuedAt,
        Instant expirationAt
) {
}
