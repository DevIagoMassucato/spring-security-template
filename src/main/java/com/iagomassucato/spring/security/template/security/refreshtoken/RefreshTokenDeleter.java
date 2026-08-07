package com.iagomassucato.spring.security.template.security.refreshtoken;

import com.iagomassucato.spring.security.template.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenDeleter {

    private final RefreshTokenRepository refreshTokenRepository;

    public void delete(RefreshTokenEntity refreshTokenEntity) {
        refreshTokenRepository.delete(refreshTokenEntity);
    }

    public void deleteByUser(UserEntity userEntity) {
        refreshTokenRepository.deleteByUserEntity(userEntity);
    }
}
