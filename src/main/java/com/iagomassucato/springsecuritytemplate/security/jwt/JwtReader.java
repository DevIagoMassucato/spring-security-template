package com.iagomassucato.springsecuritytemplate.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

@Component
public class JwtReader {

    private final JwtParser jwtParser;

    public JwtReader(SecretKey secretKey) {
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public Claims getClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    public Long getUserId(Claims claims) {
        return Long.valueOf(claims.getSubject());
    }

    public Long getSessionId(Claims claims) {
        return Long.valueOf(claims.get(JwtClaimsConstants.SESSION_ID, String.class));
    }

    public String getTokenId(Claims claims) {
        return claims.getId();
    }
}
