package com.iagomassucato.spring.security.template.security.credential;

import com.iagomassucato.spring.security.template.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CredentialCreator {

    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;

    public void createLocal(UserEntity userEntity, String password) {
        CredentialEntity credentialEntity = CredentialEntity.local(userEntity, passwordEncoder.encode(password));
        credentialRepository.save(credentialEntity);
    }
}
