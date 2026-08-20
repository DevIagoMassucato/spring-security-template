package com.iagomassucato.spring.security.template.security.session;

import com.iagomassucato.spring.security.template.user.UserEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    @Query("""
            SELECT s
            FROM SessionEntity s
            WHERE s.userEntity.id = :userId
            AND s.revokedAt IS NULL
            AND s.expiresAt > CURRENT_TIMESTAMP
            ORDER BY s.createdAt DESC
           """)
    List<SessionEntity> findActiveSessions(@Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE SessionEntity s
            SET s.revokedAt = :revokedAt
            WHERE s.userEntity = :userEntity
            AND s.revokedAt IS NULL
           """)
    void revokeAllByUserEntity(
            @Param("userEntity") UserEntity userEntity,
            @Param("revokedAt") Instant revokedAt
    );

    Optional<SessionEntity> findByIdAndUserEntity(Long sessionId, UserEntity userEntity);
    void deleteByExpiresAtBefore(Instant expiresAt);
}
