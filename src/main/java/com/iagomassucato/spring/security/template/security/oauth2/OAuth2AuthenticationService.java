package com.iagomassucato.spring.security.template.security.oauth2;

import com.iagomassucato.spring.security.template.security.credential.CredentialEntity;
import com.iagomassucato.spring.security.template.security.credential.CredentialProvider;
import com.iagomassucato.spring.security.template.security.credential.CredentialRepository;
import com.iagomassucato.spring.security.template.security.userdetails.UserDetailsImpl;
import com.iagomassucato.spring.security.template.user.UserEntity;
import com.iagomassucato.spring.security.template.user.UserFinder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2AuthenticationService {

    private final UserFinder userFinder;
    private final CredentialRepository credentialRepository;

    @Transactional
    public UserDetailsImpl authenticate(Authentication authentication) {
        OAuth2User oAuth2User = validatePrincipal(authentication);
        String email = validateOAuth2UserAttribute(oAuth2User, OAuth2Attributes.EMAIL);
        String providerId = validateOAuth2UserAttribute(oAuth2User, OAuth2Attributes.PROVIDER_ID);
        UserEntity userEntity = userFinder.findByEmailOrThrow(email);
        CredentialEntity credentialEntity = credentialRepository.findByUserEntityAndCredentialProvider(
                        userEntity,
                        CredentialProvider.GOOGLE
                ).orElseGet(() -> createCredential(userEntity, providerId));
        validateProviderId(credentialEntity, providerId);
        return new UserDetailsImpl(userEntity, credentialEntity);
    }

    private CredentialEntity createCredential(UserEntity userEntity, String providerId) {
        CredentialEntity credentialEntity = CredentialEntity.google(userEntity, providerId);
        return credentialRepository.save(credentialEntity);
    }

    private void validateProviderId(CredentialEntity credentialEntity, String providerId) {
        if (!providerId.equals(credentialEntity.getProviderId())) {
            throw new BadCredentialsException("invalid google provider id");
        }
    }

    private OAuth2User validatePrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof OAuth2User oAuth2User)) {
            throw new BadCredentialsException("authenticated principal is not an OAuth2User");
        }

        return oAuth2User;
    }

    private String validateOAuth2UserAttribute(OAuth2User oAuth2User, String attributeName) {
        String attribute = oAuth2User.getAttribute(attributeName);
        if (attribute == null) {
            throw new BadCredentialsException("OAuth2User attribute is missing: " + attributeName);
        }
        return attribute;
    }
}
