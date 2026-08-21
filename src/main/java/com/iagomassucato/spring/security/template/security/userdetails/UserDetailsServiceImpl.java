package com.iagomassucato.spring.security.template.security.userdetails;

import com.iagomassucato.spring.security.template.security.credential.CredentialEntity;
import com.iagomassucato.spring.security.template.security.credential.CredentialFinder;
import com.iagomassucato.spring.security.template.security.credential.CredentialProvider;
import com.iagomassucato.spring.security.template.user.UserEntity;
import com.iagomassucato.spring.security.template.user.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserFinder userFinder;
    private final CredentialFinder credentialFinder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = getUserEntity(username);
        return createUserDetails(userEntity);
    }

    public UserDetails loadUserById(Long id) {
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        return createUserDetails(userEntity);
    }

    private UserDetails createUserDetails(UserEntity userEntity) {
        CredentialEntity credentialEntity = credentialFinder.findByUserEntityAndCredentialProviderOrThrow(
                userEntity,
                CredentialProvider.LOCAL
        );
        return new UserDetailsImpl(userEntity, credentialEntity);
    }

    private UserEntity getUserEntity(String username) {
        try {
            return userFinder.findByUsernameOrThrow(username);
        } catch (NoSuchElementException noSuchElementException) {
            throw new UsernameNotFoundException(
                    noSuchElementException.getMessage(),
                    noSuchElementException
            );
        }
    }
}
