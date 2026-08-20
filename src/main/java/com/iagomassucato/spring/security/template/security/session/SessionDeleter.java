package com.iagomassucato.spring.security.template.security.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class SessionDeleter {

    private final SessionRepository sessionRepository;

    public void deleteByExpiresAtBefore(Instant expiresAt) {
        sessionRepository.deleteByExpiresAtBefore(expiresAt);
    }
}
