package com.iagomassucato.spring.security.template.security.session;

import com.iagomassucato.spring.security.template.shared.AbstractEntity;
import com.iagomassucato.spring.security.template.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "sessions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SessionEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

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
        super(createdAt);
        this.userEntity = validateUserEntity(userEntity);
        this.expiresAt = validateExpiresAt(createdAt, expiresAt);
        this.ipAddress = validateString(ipAddress, "ipAddress");
        this.userAgent = validateString(userAgent, "userAgent");
    }

    private UserEntity validateUserEntity(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("userEntity is required");
        }
        return userEntity;
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

    private String validateString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value;
    }
}
