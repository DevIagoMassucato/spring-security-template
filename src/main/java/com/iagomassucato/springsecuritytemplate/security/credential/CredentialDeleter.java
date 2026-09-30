package com.iagomassucato.springsecuritytemplate.security.credential;

import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CredentialDeleter {

    private final CredentialRepository credentialRepository;

    public void deleteByUser(UserEntity userEntity) {
        credentialRepository.deleteByUserEntity(userEntity);
    }
}
