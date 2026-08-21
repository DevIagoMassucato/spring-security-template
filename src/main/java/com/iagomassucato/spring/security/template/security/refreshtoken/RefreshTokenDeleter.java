package com.iagomassucato.spring.security.template.security.refreshtoken;

import com.iagomassucato.spring.security.template.security.session.SessionEntity;
import com.iagomassucato.spring.security.template.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class RefreshTokenDeleter {

    private final RefreshTokenRepository refreshTokenRepository;

    public void delete(RefreshTokenEntity refreshTokenEntity) {
        refreshTokenRepository.delete(refreshTokenEntity);
        refreshTokenRepository.flush();
    }

    public void deleteBySessionEntityUserEntity(UserEntity userEntity) {
        refreshTokenRepository.deleteBySessionEntityUserEntity(userEntity);
    }

    public void deleteBySessionEntity(SessionEntity sessionEntity) {
        refreshTokenRepository.deleteBySessionEntity(sessionEntity);
    }

    public void deleteByExpirationDateBefore(Instant expirationDate) {
        refreshTokenRepository.deleteByExpirationDateBefore(expirationDate);
    }
}
