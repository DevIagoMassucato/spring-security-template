package com.iagomassucato.springsecuritytemplate.security.refreshtoken;

import com.iagomassucato.springsecuritytemplate.security.jwt.JwtToken;
import com.iagomassucato.springsecuritytemplate.security.session.SessionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenCreator {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenEntity create(JwtToken jwtToken, SessionEntity sessionEntity) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.create(
                jwtToken.tokenId(),
                sessionEntity,
                jwtToken.issuedAt(),
                jwtToken.expirationAt()
        );
        return refreshTokenRepository.save(refreshTokenEntity);
    }
}

// the createdAt column needs to receive the same date the token was issued

