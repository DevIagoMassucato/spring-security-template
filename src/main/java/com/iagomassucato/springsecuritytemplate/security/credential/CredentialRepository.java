package com.iagomassucato.springsecuritytemplate.security.credential;

import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
    Optional<CredentialEntity> findByUserEntityAndCredentialProvider(
            UserEntity userEntity,
            CredentialProvider credentialProvider
    );
    void deleteByUserEntity(UserEntity userEntity);
}
