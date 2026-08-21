package com.iagomassucato.spring.security.template.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    public void validateAccessToken(Claims claims) {
        String tokenType = getTokenType(claims);
        if (!TokenType.ACCESS.name().equals(tokenType)) {
            throw new BadCredentialsException("access token is invalid");
        }
    }

    public void validateRefreshToken(Claims claims) {
        String tokenType = getTokenType(claims);
        if (!TokenType.REFRESH.name().equals(tokenType)) {
            throw new BadCredentialsException("refresh token is invalid");
        }
    }

    private String getTokenType(Claims claims) {
        return claims.get(JwtClaims.TYPE, String.class);
    }
}
