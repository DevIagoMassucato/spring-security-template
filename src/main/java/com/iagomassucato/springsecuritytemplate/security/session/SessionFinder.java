package com.iagomassucato.springsecuritytemplate.security.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class SessionFinder {

    private final SessionRepository sessionRepository;

    public SessionEntity findByIdAndUserEntity_IdOrThrow(Long sessionId, Long userId) {
        return sessionRepository.findByIdAndUserEntity_Id(sessionId, userId)
                .orElseThrow(() -> new NoSuchElementException("session not found with id: " + sessionId));
    }

    public List<SessionEntity> findAllActiveSessions(Long userId) {
        return sessionRepository.findAllActiveSessions(userId);
    }
}
