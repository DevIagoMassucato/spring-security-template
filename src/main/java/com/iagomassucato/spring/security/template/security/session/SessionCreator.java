package com.iagomassucato.spring.security.template.security.session;

import com.iagomassucato.spring.security.template.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class SessionCreator {

    private final SessionRepository sessionRepository;
    private final SessionProperties sessionProperties;

    public SessionEntity create(UserEntity userEntity, String ipAddress, String userAgent) {
        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plus(sessionProperties.getExpiration());
        SessionEntity sessionEntity = SessionEntity.create(
                userEntity,
                createdAt,
                expiresAt,
                ipAddress,
                userAgent
        );
        return sessionRepository.save(sessionEntity);
    }
}
