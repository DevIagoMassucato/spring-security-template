package com.iagomassucato.spring.security.template.security.refreshtoken;

import com.iagomassucato.spring.security.template.user.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "refresh_tokens",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_refresh_tokens_token_id", columnNames = "token_id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    private RefreshTokenEntity(String tokenId, UserEntity userEntity, LocalDateTime expirationDate) {
        this.tokenId = validateTokenId(tokenId);
        this.userEntity = validateUserEntity(userEntity);
        this.expirationDate = validateExpirationDate(expirationDate);
    }

    public static RefreshTokenEntity create(String tokenId, UserEntity userEntity, LocalDateTime expirationDate) {
        return new RefreshTokenEntity(tokenId, userEntity, expirationDate);
    }

    private String validateTokenId(String tokenId) {
        if (tokenId == null || tokenId.isBlank()) {
            throw new IllegalArgumentException("tokenId is required");
        }
        return tokenId;
    }

    private UserEntity validateUserEntity(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("userEntity is required");
        }
        return userEntity;
    }

    private LocalDateTime validateExpirationDate(LocalDateTime expirationDate) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("expirationDate is required");
        }
        return expirationDate;
    }
}
