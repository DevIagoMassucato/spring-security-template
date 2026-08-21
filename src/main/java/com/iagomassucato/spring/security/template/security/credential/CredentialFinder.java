package com.iagomassucato.spring.security.template.security.credential;

import com.iagomassucato.spring.security.template.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class CredentialFinder {

    private final CredentialRepository credentialRepository;

    public CredentialEntity findByUserEntityAndCredentialProviderOrThrow(
            UserEntity userEntity,
            CredentialProvider credentialProvider
    ) {
        return credentialRepository
                .findByUserEntityAndCredentialProvider(userEntity, credentialProvider)
                .orElseThrow(() -> new NoSuchElementException(
                        "credential not found for user id: " + userEntity.getId() +
                                " and provider: " + credentialProvider.name().toLowerCase()
                ));
    }
}
