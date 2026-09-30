package com.iagomassucato.spring.security.template.security.credential;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CredentialUpdater {

    private final PasswordEncoder passwordEncoder;

    public void updatePassword(CredentialEntity credentialEntity, String password) {
        validatePassword(password);
        credentialEntity.updatePasswordHash(passwordEncoder.encode(password));
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password is required");
        }
        if (password.length() > 15) {
            throw new IllegalArgumentException("password can have a maximum of 15 characters");
        }
        if (password.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException("password must not contain whitespace");
        }
    }
}
