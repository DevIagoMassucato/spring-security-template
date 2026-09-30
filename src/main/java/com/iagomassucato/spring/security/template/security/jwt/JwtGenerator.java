package com.iagomassucato.spring.security.template.security.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtToken generateAccessToken(Long userId, Long sessionId, Set<String> roles, Set<String> permissions) {
        validateAccessTokenParameters(userId, sessionId, roles, permissions);
        Instant issuedAt = Instant.now();
        Instant expirationAt = issuedAt.plus(jwtProperties.getAccessTokenExpiration());
        String tokenId = UUID.randomUUID().toString();
        String token = Jwts.builder()
                .id(tokenId)
                .issuer(jwtProperties.getIssuer())
                .audience().add(jwtProperties.getAudience()).and()
                .subject(userId.toString())
                .claim(JwtClaimsConstants.SESSION_ID, sessionId.toString())
                .claim(JwtClaimsConstants.TYPE, JwtType.ACCESS.name())
                .claim(JwtClaimsConstants.ROLES, roles)
                .claim(JwtClaimsConstants.PERMISSIONS, permissions)
                .issuedAt(Date.from(issuedAt))
                .notBefore(Date.from(issuedAt))
                .expiration(Date.from(expirationAt))
                .signWith(secretKey)
                .compact();
        return new JwtToken(tokenId, token, issuedAt, expirationAt);
    }

    public JwtToken generateRefreshToken(Long userId, Long sessionId) {
        validateRefreshTokenParameters(userId, sessionId);
        Instant issuedAt = Instant.now();
        Instant expirationAt = issuedAt.plus(jwtProperties.getRefreshTokenExpiration());
        String tokenId = UUID.randomUUID().toString();
        String token = Jwts.builder()
                .id(tokenId)
                .issuer(jwtProperties.getIssuer())
                .audience().add(jwtProperties.getAudience()).and()
                .subject(userId.toString())
                .claim(JwtClaimsConstants.SESSION_ID, sessionId.toString())
                .claim(JwtClaimsConstants.TYPE, JwtType.REFRESH.name())
                .issuedAt(Date.from(issuedAt))
                .notBefore(Date.from(issuedAt))
                .expiration(Date.from(expirationAt))
                .signWith(secretKey)
                .compact();
        return new JwtToken(tokenId, token, issuedAt, expirationAt);
    }

    public void validateAccessTokenParameters(Long userId, Long sessionId, Set<String> roles, Set<String> permissions) {
        validateUserIdAndSessionId(userId, sessionId);
        validateRoles(roles);
        validatePermissions(permissions);
    }

    public void validateRefreshTokenParameters(Long userId, Long sessionId) {
        validateUserIdAndSessionId(userId, sessionId);
    }

    private void validateUserIdAndSessionId(Long userId, Long sessionId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId is required");
        }

        if (sessionId == null || sessionId <= 0) {
            throw new IllegalArgumentException("sessionId is required");
        }
    }

    private void validateRoles(Set<String> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("roles are required");
        }
    }

    private void validatePermissions(Set<String> permissions) {
        if (permissions == null) {
            throw new IllegalArgumentException("permissions cannot be null");
        }
    }
}