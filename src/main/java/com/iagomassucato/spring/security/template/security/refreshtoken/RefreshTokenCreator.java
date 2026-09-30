package com.iagomassucato.spring.security.template.security.refreshtoken;

import com.iagomassucato.spring.security.template.security.jwt.JwtToken;
import com.iagomassucato.spring.security.template.security.session.SessionEntity;
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

