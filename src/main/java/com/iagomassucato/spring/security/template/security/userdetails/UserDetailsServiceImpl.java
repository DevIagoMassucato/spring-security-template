package com.iagomassucato.spring.security.template.security.userdetails;

import com.iagomassucato.spring.security.template.security.credential.CredentialProvider;
import com.iagomassucato.spring.security.template.security.credential.CredentialEntity;
import com.iagomassucato.spring.security.template.security.credential.CredentialRepository;
import com.iagomassucato.spring.security.template.user.UserEntity;
import com.iagomassucato.spring.security.template.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username: " + username));
        CredentialEntity credentialEntity = credentialRepository
                .findByUserEntityAndCredentialProvider(userEntity, CredentialProvider.LOCAL)
                .orElseThrow(() -> new NoSuchElementException(
                        "credential not found for username: " + username +
                                " and provider: " + CredentialProvider.LOCAL.name().toLowerCase()
                ));
        return new UserDetailsImpl(userEntity, credentialEntity);
    }
}
