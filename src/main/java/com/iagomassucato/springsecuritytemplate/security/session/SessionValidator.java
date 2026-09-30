package com.iagomassucato.springsecuritytemplate.security.session;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class  SessionValidator {

    public void validate(SessionEntity sessionEntity, Long userId, Long sessionId) {
        if (!sessionEntity.getId().equals(sessionId)) {
            throw new BadCredentialsException("sessionId is invalid");
        }

        if (!sessionEntity.getUserEntity().getId().equals(userId)) {
            throw new BadCredentialsException("userId is invalid");
        }

        if (sessionEntity.getRevokedAt() != null) {
            throw new BadCredentialsException("this session has already been revoked");
        }
    }
}
