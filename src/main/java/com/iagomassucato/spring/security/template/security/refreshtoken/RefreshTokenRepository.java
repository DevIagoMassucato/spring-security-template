package com.iagomassucato.spring.security.template.security.refreshtoken;

import com.iagomassucato.spring.security.template.security.session.SessionEntity;
import com.iagomassucato.spring.security.template.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByTokenId(String tokenId);
    void deleteBySessionEntityUserEntity(UserEntity userEntity);
    void deleteBySessionEntity(SessionEntity sessionEntity);
    void deleteByExpirationDateBefore(Instant dateTime);
}
