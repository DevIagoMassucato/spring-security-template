package com.iagomassucato.spring.security.template.security.oauth2;

import com.iagomassucato.spring.security.template.security.credential.CredentialProvider;
import com.iagomassucato.spring.security.template.security.credential.CredentialEntity;
import com.iagomassucato.spring.security.template.security.credential.CredentialRepository;
import com.iagomassucato.spring.security.template.security.userdetails.UserDetailsImpl;
import com.iagomassucato.spring.security.template.user.UserEntity;
import com.iagomassucato.spring.security.template.user.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2AuthenticationService {

    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String PROVIDER_ID_ATTRIBUTE = "sub";
    private final UserFinder userFinder;
    private final CredentialRepository credentialRepository;

    @Transactional
    public UserDetailsImpl authenticate(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get(EMAIL_ATTRIBUTE);
        String providerId = (String) attributes.get(PROVIDER_ID_ATTRIBUTE);
        UserEntity userEntity = userFinder.findByEmail(email);
        CredentialEntity credentialEntity = credentialRepository
                .findByUserEntityAndCredentialProvider(userEntity, CredentialProvider.GOOGLE)
                .orElseGet(() -> createCredential(userEntity, providerId));
        validateProviderId(credentialEntity, providerId);
        return new UserDetailsImpl(userEntity, credentialEntity);
    }

    private CredentialEntity createCredential(UserEntity userEntity, String providerId) {
        CredentialEntity credentialEntity = CredentialEntity.google(userEntity, providerId);
        return credentialRepository.save(credentialEntity);
    }

    private void validateProviderId(CredentialEntity credentialEntity, String providerId) {
        if (!providerId.equals(credentialEntity.getProviderId())) {
            throw new BadCredentialsException("invalid Google provider id");
        }
    }
}
