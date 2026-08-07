package com.iagomassucato.spring.security.template.security.resetpassword;

import com.iagomassucato.spring.security.template.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPasswordEntity, Long> {
    Optional<ResetPasswordEntity> findByUserEntityAndCode(UserEntity userEntity, String code);
    void deleteByUserEntityAndUsedFalse(UserEntity userEntity);
}