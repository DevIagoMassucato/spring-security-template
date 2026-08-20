package com.iagomassucato.spring.security.template.security.session;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class SessionValidator {

    public void validate(SessionEntity sessionEntity, Long userId, Long sessionId) {
        if (!sessionEntity.getId().equals(sessionId)) {
            throw new BadCredentialsException("session is invalid");
        }

        if (!sessionEntity.getUserEntity().getId().equals(userId)) {
            throw new BadCredentialsException("user is invalid");
        }

        if (sessionEntity.getRevokedAt() != null) {
            throw new BadCredentialsException("this session has already been revoked");
        }
    }
}
