package com.iagomassucato.spring.security.template.security.refreshtoken;

import com.iagomassucato.spring.security.template.security.auth.AuthResponse;
import com.iagomassucato.spring.security.template.security.jwt.JwtService;
import com.iagomassucato.spring.security.template.security.jwt.JwtToken;
import com.iagomassucato.spring.security.template.security.jwt.JwtValidator;
import com.iagomassucato.spring.security.template.security.session.SessionEntity;
import com.iagomassucato.spring.security.template.security.session.SessionValidator;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenCreator refreshTokenCreator;
    private final RefreshTokenFinder refreshTokenFinder;
    private final RefreshTokenDeleter refreshTokenDeleter;
    private final JwtService jwtService;
    private final JwtValidator jwtValidator;
    private final SessionValidator sessionValidator;

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        Claims claims = jwtService.getClaims(refreshToken);
        jwtValidator.validateRefreshToken(claims);
        String tokenId = jwtService.getTokenId(claims);
        Long userId = jwtService.getSubject(claims);
        Long sessionId = jwtService.getSessionId(claims);
        RefreshTokenEntity refreshTokenEntity = refreshTokenFinder.findByTokenIdOrThrow(tokenId);
        SessionEntity sessionEntity = refreshTokenEntity.getSessionEntity();
        sessionValidator.validate(sessionEntity, userId, sessionId);
        refreshTokenDeleter.delete(refreshTokenEntity);
        JwtToken newAccessToken = jwtService.generateAccessToken(userId, sessionId);
        JwtToken newRefreshToken = jwtService.generateRefreshToken(userId, sessionId);
        refreshTokenCreator.create(newRefreshToken, sessionEntity);
        return new AuthResponse(newAccessToken.token(), newRefreshToken.token());
    }

    @Transactional
    public void logout(RefreshTokenRequest refreshTokenRequest) {
        Claims claims = jwtService.getClaims(refreshTokenRequest.refreshToken());
        jwtValidator.validateRefreshToken(claims);
        String tokenId = jwtService.getTokenId(claims);
        Long userId = jwtService.getSubject(claims);
        Long sessionId = jwtService.getSessionId(claims);
        RefreshTokenEntity refreshTokenEntity = refreshTokenFinder.findByTokenIdOrThrow(tokenId);
        SessionEntity sessionEntity = refreshTokenEntity.getSessionEntity();
        sessionValidator.validate(sessionEntity, userId, sessionId);
        refreshTokenDeleter.delete(refreshTokenEntity);
        sessionEntity.revoke();
    }
}