package com.iagomassucato.spring.security.template.security.credential;

import com.iagomassucato.spring.security.template.user.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "credentials",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_credentials_provider_id", columnNames = "provider_id"),
                @UniqueConstraint(name = "uk_credentials_user_id_provider", columnNames = {"user_id", "provider"})
        })
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class CredentialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    @Column(name= "provider", nullable = false)
    private CredentialProvider credentialProvider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "password_hash")
    private String passwordHash;

    public static CredentialEntity local(UserEntity userEntity, String passwordHash) {
        return new CredentialEntity(
                userEntity,
                CredentialProvider.LOCAL,
                null,
                passwordHash
        );
    }

    public static CredentialEntity google(UserEntity userEntity, String providerId) {
        return new CredentialEntity(
                userEntity,
                CredentialProvider.GOOGLE,
                providerId,
                null
        );
    }

    public void updatePasswordHash(String passwordHash) {
        this.passwordHash = validatePasswordHash(credentialProvider, passwordHash);
    }

    private CredentialEntity(UserEntity userEntity, CredentialProvider credentialProvider, String providerId, String passwordHash) {
        this.userEntity = validateUserEntity(userEntity);
        this.credentialProvider = validateCredentialProvider(credentialProvider);
        this.providerId = validateProviderId(credentialProvider, providerId);
        this.passwordHash = validatePasswordHash(credentialProvider, passwordHash);
    }

    private UserEntity validateUserEntity(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("userEntity is required");
        }
        return userEntity;
    }

    private CredentialProvider validateCredentialProvider(CredentialProvider credentialProvider) {
        if (credentialProvider == null) {
            throw new IllegalArgumentException("credentialProvider is required");
        }
        return credentialProvider;
    }

    private String validateProviderId(CredentialProvider credentialProvider, String providerId) {
        if (credentialProvider == CredentialProvider.GOOGLE && (providerId == null || providerId.isBlank())) {
            throw new IllegalArgumentException("providerId is required");
        }
        return providerId;
    }

    private String validatePasswordHash(CredentialProvider credentialProvider, String passwordHash) {
        if (credentialProvider == CredentialProvider.LOCAL && (passwordHash == null || passwordHash.isBlank())) {
            throw new IllegalArgumentException("passwordHash is required");
        }
        return passwordHash;
    }
}
