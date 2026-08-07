package com.iagomassucato.spring.security.template.security.jwt;

import com.iagomassucato.spring.security.template.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String CLAIM_TYPE = "type";

    private final JwtProperties jwtProperties;

    public JwtToken generateAccessToken(UserEntity userEntity) {
        return generateToken(userEntity.getId(), TokenType.ACCESS, jwtProperties.getAccessTokenExpiration());
    }

    public JwtToken generateRefreshToken(UserEntity userEntity) {
        return generateToken(userEntity.getId(), TokenType.REFRESH, jwtProperties.getRefreshTokenExpiration());
    }

    public Claims getClaims(String token) {
        return getParser().parseClaimsJws(token).getBody();
    }

    public Long getSubject(Claims claims) {
        return Long.valueOf(claims.getSubject());
    }

    public String getTokenId(Claims claims) {
        return claims.getId();
    }

    public boolean isAccessToken(Claims claims) {
        return TokenType.ACCESS.name().equals(claims.get(CLAIM_TYPE, String.class));
    }

    public boolean isRefreshToken(Claims claims) {
        return TokenType.REFRESH.name().equals(claims.get(CLAIM_TYPE, String.class));
    }

    private JwtToken generateToken(Long userId, TokenType tokenType, Duration expiration) {
        String tokenId = UUID.randomUUID().toString();
        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + expiration.toMillis());
        String token = Jwts.builder()
                .setId(tokenId)
                .setSubject(userId.toString())
                .claim(CLAIM_TYPE, tokenType.name())
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
        return new JwtToken(
                tokenId,
                token,
                expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }

    private JwtParser getParser() {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey());
    }
}