package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.security.refreshtoken.RefreshTokenDeleter;
import com.iagomassucato.spring.security.template.security.session.SessionRevoker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSessionRevoker {

    private final SessionRevoker sessionRevoker;
    private final RefreshTokenDeleter refreshTokenDeleter;

    public void revokeAll(UserEntity userEntity) {
        sessionRevoker.revokeAllByUserEntity(userEntity);
        refreshTokenDeleter.deleteBySessionEntityUserEntity(userEntity);
    }
}