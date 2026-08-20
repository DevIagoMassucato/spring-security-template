package com.iagomassucato.spring.security.template.security.session;

import com.iagomassucato.spring.security.template.user.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "sessions")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private String userAgent;

    private Instant revokedAt;

    public static SessionEntity create(
            UserEntity userEntity,
            Instant createdAt,
            Instant expiresAt,
            String ipAddress,
            String userAgent
    ) {
        return new SessionEntity(
                userEntity,
                createdAt,
                expiresAt,
                ipAddress,
                userAgent
        );
    }

    public void revoke() {
        this.revokedAt = Instant.now();
    }

    private SessionEntity(
            UserEntity userEntity,
            Instant createdAt,
            Instant expiresAt,
            String ipAddress,
            String userAgent
    ) {
        this.userEntity = validateUserEntity(userEntity);
        this.createdAt = validateCreatedAt(createdAt);
        this.expiresAt = validateExpiresAt(expiresAt);
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    private UserEntity validateUserEntity(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("userEntity is required");
        }
        return userEntity;
    }

    private Instant validateCreatedAt(Instant createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("createdAt is required");
        }
        return createdAt;
    }

    private Instant validateExpiresAt(Instant expiresAt) {
        if (expiresAt == null) {
            throw new IllegalArgumentException("expiresAt is required");
        }
        if (!expiresAt.isAfter(createdAt)) {
            throw new IllegalArgumentException("expiresAt must be after createdAt");
        }
        return expiresAt;
    }
}
