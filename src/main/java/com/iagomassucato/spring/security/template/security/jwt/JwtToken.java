package com.iagomassucato.spring.security.template.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.Instant;

@AllArgsConstructor
@Getter
public class JwtToken {
    private final String tokenId;
    private final String token;
    private final Instant expirationDate;
}
