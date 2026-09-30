package com.iagomassucato.springsecuritytemplate.security.refreshtoken;

import com.iagomassucato.springsecuritytemplate.accesscontrol.permission.PermissionEntity;
import com.iagomassucato.springsecuritytemplate.accesscontrol.role.RoleEntity;
import com.iagomassucato.springsecuritytemplate.security.auth.AuthResponse;
import com.iagomassucato.springsecuritytemplate.security.jwt.JwtGenerator;
import com.iagomassucato.springsecuritytemplate.security.jwt.JwtReader;
import com.iagomassucato.springsecuritytemplate.security.jwt.JwtToken;
import com.iagomassucato.springsecuritytemplate.security.jwt.JwtRefreshTokenValidator;
import com.iagomassucato.springsecuritytemplate.security.session.SessionEntity;
import com.iagomassucato.springsecuritytemplate.security.session.SessionValidator;
import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenCreator refreshTokenCreator;
    private final RefreshTokenFinder refreshTokenFinder;
    private final RefreshTokenDeleter refreshTokenDeleter;
    private final JwtGenerator jwtGenerator;
    private final JwtReader jwtReader;
    private final JwtRefreshTokenValidator jwtRefreshTokenValidator;
    private final SessionValidator sessionValidator;

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        Claims claims = jwtReader.getClaims(refreshToken);
        jwtRefreshTokenValidator.validate(claims);
        String tokenId = jwtReader.getTokenId(claims);
        Long userId = jwtReader.getUserId(claims);
        Long sessionId = jwtReader.getSessionId(claims);
        RefreshTokenEntity refreshTokenEntity = refreshTokenFinder.findByTokenIdOrThrow(tokenId);
        SessionEntity sessionEntity = refreshTokenEntity.getSessionEntity();
        sessionValidator.validate(sessionEntity, userId, sessionId);
        refreshTokenDeleter.delete(refreshTokenEntity);
        UserEntity userEntity = sessionEntity.getUserEntity();
        Set<String> roles = userEntity.getRoleEntitySet()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
        Set<String> permissions = userEntity.getRoleEntitySet()
                .stream()
                .flatMap(role -> role.getPermissionEntitySet().stream())
                .map(PermissionEntity::getName)
                .collect(Collectors.toSet());
        JwtToken newAccessToken = jwtGenerator.generateAccessToken(userId, sessionId, roles, permissions);
        JwtToken newRefreshToken = jwtGenerator.generateRefreshToken(userId, sessionId);
        refreshTokenCreator.create(newRefreshToken, sessionEntity);
        return new AuthResponse(newAccessToken.token(), newRefreshToken.token());
    }

    @Transactional
    public void logout(RefreshTokenRequest refreshTokenRequest) {
        Claims claims = jwtReader.getClaims(refreshTokenRequest.refreshToken());
        jwtRefreshTokenValidator.validate(claims);
        String tokenId = jwtReader.getTokenId(claims);
        Long userId = jwtReader.getUserId(claims);
        Long sessionId = jwtReader.getSessionId(claims);
        RefreshTokenEntity refreshTokenEntity = refreshTokenFinder.findByTokenIdOrThrow(tokenId);
        SessionEntity sessionEntity = refreshTokenEntity.getSessionEntity();
        sessionValidator.validate(sessionEntity, userId, sessionId);
        refreshTokenDeleter.delete(refreshTokenEntity);
        sessionEntity.revoke();
    }
}