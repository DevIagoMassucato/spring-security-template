package com.iagomassucato.spring.security.template.security.refreshtoken;

import com.iagomassucato.spring.security.template.security.auth.AuthResponse;
import com.iagomassucato.spring.security.template.security.jwt.JwtService;
import com.iagomassucato.spring.security.template.security.jwt.JwtToken;
import com.iagomassucato.spring.security.template.user.UserEntity;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenFinder refreshTokenFinder;
    private final RefreshTokenDeleter refreshTokenDeleter;
    private final JwtService jwtService;

    public RefreshTokenEntity create(JwtToken jwtToken, UserEntity userEntity) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.create(
                jwtToken.getTokenId(),
                userEntity,
                jwtToken.getExpirationDate()
        );
        return refreshTokenRepository.save(refreshTokenEntity);
    }

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        Claims claims = jwtService.getClaims(refreshToken);
        if (!jwtService.isRefreshToken(claims)) {
            throw new RuntimeException("invalid refresh token type");
        }
        String tokenId = jwtService.getTokenId(claims);
        RefreshTokenEntity refreshTokenEntity = refreshTokenFinder.findByTokenIdOrThrow(tokenId);
        if (refreshTokenEntity.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("refresh token expired");
        }
        UserEntity userEntity = refreshTokenEntity.getUserEntity();
        refreshTokenDeleter.delete(refreshTokenEntity);
        JwtToken newAccessToken = jwtService.generateAccessToken(userEntity);
        JwtToken newRefreshToken = jwtService.generateRefreshToken(userEntity);
        create(newRefreshToken, userEntity);
        return new AuthResponse(
                newAccessToken.getToken(),
                newRefreshToken.getToken()
        );
    }

    public void delete(RefreshTokenRequest refreshTokenRequest) {
        Claims claims = jwtService.getClaims(refreshTokenRequest.getRefreshToken());
        String tokenId = jwtService.getTokenId(claims);
        RefreshTokenEntity refreshTokenEntity = refreshTokenFinder.findByTokenIdOrThrow(tokenId);
        refreshTokenDeleter.delete(refreshTokenEntity);
    }
}