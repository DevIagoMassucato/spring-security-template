package com.iagomassucato.spring.security.template.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final String SECRET_KEY = "my-secret-key";
    private static final String CLAIM_TYPE = "type";
    private static final String ACCESS_TOKEN = "access";
    private static final String REFRESH_TOKEN = "refresh";
    private static final long ACCESS_TOKEN_EXPIRATION = 5 * 60 * 1000L;
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L;

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, ACCESS_TOKEN, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, REFRESH_TOKEN, REFRESH_TOKEN_EXPIRATION);
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            getParser().parseClaimsJws(token);
            return true;
        } catch (JwtException exception) {
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        return ACCESS_TOKEN.equals(extractClaims(token).get(CLAIM_TYPE, String.class));
    }

    public boolean isRefreshToken(String token) {
        return REFRESH_TOKEN.equals(extractClaims(token).get(CLAIM_TYPE, String.class));
    }

    private String generateToken(UserDetails userDetails, String type, long expiration) {
        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + expiration);

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(userDetails.getUsername())
                .claim(CLAIM_TYPE, type)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Claims extractClaims(String token) {
        return getParser()
                .parseClaimsJws(token)
                .getBody();
    }

    private JwtParser getParser() {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY);
    }
}
