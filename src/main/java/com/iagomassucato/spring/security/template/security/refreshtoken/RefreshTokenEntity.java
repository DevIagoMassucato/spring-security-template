package com.iagomassucato.spring.security.template.security.refreshtoken;

import com.iagomassucato.spring.security.template.security.session.SessionEntity;
import com.iagomassucato.spring.security.template.shared.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Table(
        name = "refresh_tokens",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_refresh_tokens_token_id", columnNames = "token_id"),
                @UniqueConstraint(name = "uk_refresh_tokens_session_id", columnNames = "session_id")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshTokenEntity extends AbstractEntity {

    @Column(name = "token_id", nullable = false, length = 36)
    private String tokenId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private SessionEntity sessionEntity;

    @Column(nullable = false)
    private Instant expiresAt;


    public static RefreshTokenEntity create(
            String tokenId,
            SessionEntity sessionEntity,
            Instant createdAt,
            Instant expiresAt
    ) {
        return new RefreshTokenEntity(tokenId, sessionEntity, createdAt, expiresAt);
    }

    private RefreshTokenEntity(
            String tokenId,
            SessionEntity sessionEntity,
            Instant createdAt,
            Instant expiresAt
    ) {
        super(createdAt);
        this.tokenId = validateTokenId(tokenId);
        this.sessionEntity = validateSessionEntity(sessionEntity);
        this.expiresAt = validateExpiresAt(createdAt, expiresAt);
    }

    private String validateTokenId(String tokenId) {
        if (tokenId == null || tokenId.isBlank()) {
            throw new IllegalArgumentException("tokenId is required");
        }
        return tokenId;
    }

    private SessionEntity validateSessionEntity(SessionEntity sessionEntity) {
        if (sessionEntity == null) {
            throw new IllegalArgumentException("sessionEntity is required");
        }

        return sessionEntity;
    }

    private Instant validateExpiresAt(Instant createdAt, Instant expiresAt) {
        if (expiresAt == null) {
            throw new IllegalArgumentException("expiresAt is required");
        }
        if (!expiresAt.isAfter(createdAt)) {
            throw new IllegalArgumentException("expiresAt must be after createdAt");
        }
        return expiresAt;
    }
}
