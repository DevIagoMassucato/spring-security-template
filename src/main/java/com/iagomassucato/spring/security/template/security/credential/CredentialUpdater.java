package com.iagomassucato.spring.security.template.security.credential;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CredentialUpdater {

    private final PasswordEncoder passwordEncoder;

    public void updatePassword(CredentialEntity credentialEntity, String password) {
        credentialEntity.updatePasswordHash(passwordEncoder.encode(password));
    }
}
