package com.iagomassucato.spring.security.template.security.jwt;

import com.iagomassucato.spring.security.template.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final String CLAIM_TYPE = "type";

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

        byte[] keyBytes = Decoders.BASE64.decode(
                jwtProperties.getSecretKey()
        );

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        this.jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }

    public JwtToken generateAccessToken(UserEntity userEntity) {
        return generateToken(
                userEntity.getId(),
                TokenType.ACCESS,
                jwtProperties.getAccessTokenExpiration()
        );
    }

    public JwtToken generateRefreshToken(UserEntity userEntity) {
        return generateToken(
                userEntity.getId(),
                TokenType.REFRESH,
                jwtProperties.getRefreshTokenExpiration()
        );
    }

    public Claims getClaims(String token) {
        return jwtParser
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getSubject(Claims claims) {
        return Long.valueOf(claims.getSubject());
    }

    public String getTokenId(Claims claims) {
        return claims.getId();
    }

    public boolean isAccessToken(Claims claims) {
        return TokenType.ACCESS.name()
                .equals(claims.get(CLAIM_TYPE, String.class));
    }

    public boolean isRefreshToken(Claims claims) {
        return TokenType.REFRESH.name()
                .equals(claims.get(CLAIM_TYPE, String.class));
    }

    private JwtToken generateToken(
            Long userId,
            TokenType tokenType,
            Duration expiration
    ) {
        String tokenId = UUID.randomUUID().toString();

        Instant issuedAt = Instant.now();
        Instant expirationDate = issuedAt.plus(expiration);

        String token = Jwts.builder()
                .id(tokenId)
                .subject(userId.toString())
                .claim(CLAIM_TYPE, tokenType.name())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expirationDate))
                .signWith(secretKey)
                .compact();

        return new JwtToken(
                tokenId,
                token,
                expirationDate
        );
    }
}