package com.iagomassucato.spring.security.template.security.me;

import com.iagomassucato.spring.security.template.security.credential.*;
import com.iagomassucato.spring.security.template.security.userdetails.UserDetailsImpl;
import com.iagomassucato.spring.security.template.shared.PatchValidator;
import com.iagomassucato.spring.security.template.user.UserEntity;
import com.iagomassucato.spring.security.template.user.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MeService {

    private final UserFinder userFinder;
    private final CredentialFinder credentialFinder;
    private final CredentialUpdater credentialUpdater;
    private final CredentialValidator credentialValidator;
    private final PatchValidator patchValidator;

    @Transactional
    public MeResponse update(MePatchRequest mePatchRequest) {
        patchValidator.validate(mePatchRequest);
        UserEntity userEntity = getAuthenticatedUser();
        CredentialEntity credentialEntity = credentialFinder
                .findByUserEntityAndCredentialProvideOrThrow(userEntity, CredentialProvider.LOCAL);
        credentialValidator.validateCurrentPassword(credentialEntity, mePatchRequest.getCurrentPassword());
        if (mePatchRequest.getUsername() != null) {
            userEntity.updateUsername(mePatchRequest.getUsername());
        }
        if (mePatchRequest.getEmail() != null) {
            userEntity.updateEmail(mePatchRequest.getEmail());
        }
        if (mePatchRequest.getPassword() != null) {
            credentialUpdater.updatePassword(credentialEntity, mePatchRequest.getPassword());
        }
        return MeResponse.fromEntity(userEntity);
    }

    @Transactional
    public MeResponse replace(MeRequest meRequest) {
        UserEntity userEntity = getAuthenticatedUser();
        CredentialEntity credentialEntity = credentialFinder
                .findByUserEntityAndCredentialProvideOrThrow(userEntity, CredentialProvider.LOCAL);
        credentialValidator.validateCurrentPassword(credentialEntity, meRequest.getCurrentPassword());
        userEntity.updateUsername(meRequest.getUsername());
        userEntity.updateEmail(meRequest.getEmail());
        credentialUpdater.updatePassword(credentialEntity, meRequest.getPassword());
        return MeResponse.fromEntity(userEntity);
    }

    public MeResponse findMe() {
        return MeResponse.fromEntity(getAuthenticatedUser());
    }

    private UserEntity getAuthenticatedUser() {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userFinder.findByIdOrThrow(userDetails.getUserEntity().getId());
    }
}
