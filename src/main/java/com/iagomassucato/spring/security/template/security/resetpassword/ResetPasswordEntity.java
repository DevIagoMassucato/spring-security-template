package com.iagomassucato.spring.security.template.security.resetpassword;

import com.iagomassucato.spring.security.template.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reset_passwords")
@NoArgsConstructor
@Getter
public class ResetPasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private OffsetDateTime expiresAt;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    public static ResetPasswordEntity create(UserEntity userEntity, String code) {
        return new ResetPasswordEntity(
                userEntity,
                code,
                OffsetDateTime.now().plusMinutes(10)
        );
    }

    public void markAsUsed() {
        this.used = true;
    }

    public void validateCodeStatus() {
        if (used) {
            throw new IllegalStateException("code already used");
        }

        if (expiresAt.isBefore(OffsetDateTime.now())) {
            throw new IllegalStateException("code expired");
        }
    }

    private ResetPasswordEntity(UserEntity userEntity, String code, OffsetDateTime expiresAt) {
        this.userEntity = validateUserEntity(userEntity);
        this.code = validateCode(code);
        this.used = false;
        this.expiresAt = validateExpiresAt(expiresAt);
        this.createdAt = OffsetDateTime.now();
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

    private OffsetDateTime validateExpiresAt(OffsetDateTime expiresAt) {
        if (expiresAt == null) {
            throw new IllegalArgumentException("expiresAt is required");
        }
        return expiresAt;
    }
}
