package com.iagomassucato.springsecuritytemplate.security.refreshtoken;

import com.iagomassucato.springsecuritytemplate.security.session.SessionEntity;
import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByTokenId(String tokenId);
    void deleteBySessionEntityUserEntity(UserEntity userEntity);
    void deleteBySessionEntity(SessionEntity sessionEntity);
    void deleteByExpiresAtBefore(Instant expiresAt);
}
