package com.iagomassucato.springsecuritytemplate.security.session;

import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class SessionRevoker {

    private final SessionRepository sessionRepository;

    public void revokeAllByUserEntity(UserEntity userEntity) {
        sessionRepository.revokeAllByUserEntity(userEntity, Instant.now());
    }
}
