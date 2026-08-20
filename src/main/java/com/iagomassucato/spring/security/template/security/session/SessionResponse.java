package com.iagomassucato.spring.security.template.security.session;

import java.time.Instant;

public record SessionResponse(
        Long id,
        Instant createdAt,
        Instant expiresAt,
        String ipAddress,
        String userAgent
) {

    public static SessionResponse fromEntity(SessionEntity sessionEntity) {
        return new SessionResponse(
                sessionEntity.getId(),
                sessionEntity.getCreatedAt(),
                sessionEntity.getExpiresAt(),
                sessionEntity.getIpAddress(),
                sessionEntity.getUserAgent()
        );
    }
}
