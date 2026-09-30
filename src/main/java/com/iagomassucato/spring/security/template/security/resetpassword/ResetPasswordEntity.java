package com.iagomassucato.spring.security.template.security.resetpassword;

import com.iagomassucato.spring.security.template.shared.AbstractEntity;
import com.iagomassucato.spring.security.template.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "reset_passwords")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ResetPasswordEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private Instant expiresAt;

    public static ResetPasswordEntity create(
            UserEntity userEntity,
            String code,
            Instant createdAt,
            Instant expiresAt
    ) {
        return new ResetPasswordEntity(
                userEntity,
                code,
                createdAt,
                expiresAt
        );
    }

    public void markAsUsed() {
        this.used = true;
    }

    public void validateCodeStatus() {
        if (used) {
            throw new IllegalStateException("code already used");
        }
        if (!expiresAt.isAfter(Instant.now())) {
            throw new IllegalStateException("code expired");
        }
    }

    private ResetPasswordEntity(UserEntity userEntity, String code, Instant createdAt, Instant expiresAt) {
        super(createdAt);
        this.userEntity = validateUserEntity(userEntity);
        this.code = validateCode(code);
        this.used = false;
        this.expiresAt = validateExpiresAt(createdAt, expiresAt);
    }

    private UserEntity validateUserEntity(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("userEntity is required");
        }
        return userEntity;
    }

    private String validateCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code is required");
        }
        return code;
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
