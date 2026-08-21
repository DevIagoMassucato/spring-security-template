package com.iagomassucato.spring.security.template.security.credential;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CredentialValidator {

    private final PasswordEncoder passwordEncoder;

    public void validateCurrentPassword(CredentialEntity credentialEntity, String currentPassword) {
        if (currentPassword == null || !passwordEncoder.matches(currentPassword, credentialEntity.getPasswordHash())) {
            throw new BadCredentialsException("the current password is invalid");
        }
    }
}
