package com.iagomassucato.springsecuritytemplate.security.resetpassword;

import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPasswordEntity, Long> {
    Optional<ResetPasswordEntity> findByUserEntityAndCode(UserEntity userEntity, String code);
    void deleteByUserEntityAndUsedFalse(UserEntity userEntity);
    void deleteByExpiresAtBefore(Instant dateTime);
}