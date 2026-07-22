package com.iagomassucato.spring.security.template.security.credential;

import com.iagomassucato.spring.security.template.user.UserEntity;
import com.iagomassucato.spring.security.template.user.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final CredentialRepository credentialRepository;
    private final UserFinder userFinder;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createLocalCredential(UserEntity userEntity, String password) {
        CredentialEntity credentialEntity = CredentialEntity.local(userEntity, encodePassword(password));
        credentialRepository.save(credentialEntity);
    }

    @Transactional
    public void updatePassword(Long user, String password) {
        UserEntity userEntity = userFinder.findByIdOrThrow(user);
        CredentialEntity credentialEntity = credentialRepository
                .findByUserEntityAndCredentialProvider(userEntity, CredentialProvider.LOCAL)
                .orElseThrow(() -> new NoSuchElementException(
                        "credential not found for user id: " + user +
                                " and provider: " + CredentialProvider.LOCAL.name().toLowerCase()
                ));
        credentialEntity.updatePasswordHash(encodePassword(password));
        credentialRepository.save(credentialEntity);
    }

    public void deleteByUser(UserEntity userEntity) {
        credentialRepository.deleteByUserEntity(userEntity);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
