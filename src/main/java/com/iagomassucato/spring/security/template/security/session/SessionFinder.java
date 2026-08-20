package com.iagomassucato.spring.security.template.security.session;

import com.iagomassucato.spring.security.template.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class SessionFinder {

    private final SessionRepository sessionRepository;

    public SessionEntity findByIdAndUserEntityOrThrow(Long sessionId, UserEntity userEntity) {
        return sessionRepository.findByIdAndUserEntity(sessionId, userEntity)
                .orElseThrow(() -> new NoSuchElementException("session not found with id: " + sessionId));
    }

    public List<SessionEntity> findActiveSessions(Long userId) {
        return sessionRepository.findActiveSessions(userId);
    }
}
