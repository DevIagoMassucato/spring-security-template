package com.iagomassucato.spring.security.template.security.refreshtoken;

import com.iagomassucato.spring.security.template.security.session.SessionEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
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
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_id", nullable = false, length = 36)
    private String tokenId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private SessionEntity sessionEntity;

    @Column(nullable = false)
    private Instant expirationDate;


    public static RefreshTokenEntity create(String tokenId, SessionEntity sessionEntity, Instant expirationDate) {
        return new RefreshTokenEntity(tokenId, sessionEntity, expirationDate);
    }

    private RefreshTokenEntity(String tokenId, SessionEntity sessionEntity, Instant expirationDate) {
        this.tokenId = validateTokenId(tokenId);
        this.sessionEntity = validateSessionEntity(sessionEntity);
        this.expirationDate = validateExpirationDate(expirationDate);
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

    private Instant validateExpirationDate(Instant expirationDate) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("expirationDate is required");
        }
        return expirationDate;
    }
}
