package com.iagomassucato.spring.security.template.security.refreshtoken;

import com.iagomassucato.spring.security.template.security.auth.AuthResponse;
import com.iagomassucato.spring.security.template.security.jwt.JwtService;
import com.iagomassucato.spring.security.template.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenFinder refreshTokenFinder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public RefreshTokenEntity create(String refreshToken, UserEntity userEntity) {
        RefreshTokenEntity refreshTokenEntity =
                RefreshTokenEntity.builder()
                        .token(refreshToken)
                        .expiration(LocalDateTime.now().plusDays(7))
                        .revoked(false)
                        .userEntity(userEntity)
                        .build();
        return refreshTokenRepository.save(refreshTokenEntity);
    }

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (!jwtService.isTokenValid(refreshToken)) {
            throw new RuntimeException("invalid refresh token");
        }
        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new RuntimeException("invalid refresh token type");
        }
        RefreshTokenEntity refreshTokenEntity = refreshTokenFinder.findByTokenOrThrow(refreshToken);
        if (refreshTokenEntity.isExpired()) {
            refreshTokenEntity.revoke();
            throw new RuntimeException("refresh token expired");
        }
        if (refreshTokenEntity.isRevoked()) {
            throw new RuntimeException("refresh token revoked");
        }
        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        refreshTokenEntity.revoke();
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);
        create(newRefreshToken, refreshTokenEntity.getUserEntity());
        return new AuthResponse(newAccessToken, newRefreshToken
        );
    }

    @Transactional
    public void revoke(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        RefreshTokenEntity refreshTokenEntity = refreshTokenFinder.findByTokenOrThrow(refreshToken);
        if (!refreshTokenEntity.isRevoked()) {
            refreshTokenEntity.revoke();
        }
    }
}
