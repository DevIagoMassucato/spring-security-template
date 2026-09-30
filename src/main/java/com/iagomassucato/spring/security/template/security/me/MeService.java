package com.iagomassucato.spring.security.template.security.me;

import com.iagomassucato.spring.security.template.security.auth.AuthUser;
import com.iagomassucato.spring.security.template.security.credential.*;
import com.iagomassucato.spring.security.template.security.refreshtoken.RefreshTokenDeleter;
import com.iagomassucato.spring.security.template.security.session.SessionEntity;
import com.iagomassucato.spring.security.template.security.session.SessionFinder;
import com.iagomassucato.spring.security.template.security.session.SessionResponse;
import com.iagomassucato.spring.security.template.shared.PatchValidator;
import com.iagomassucato.spring.security.template.user.UserEntity;
import com.iagomassucato.spring.security.template.user.UserFinder;
import com.iagomassucato.spring.security.template.user.UserSessionRevoker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeService {

    private final UserFinder userFinder;
    private final CredentialFinder credentialFinder;
    private final CredentialUpdater credentialUpdater;
    private final CredentialValidator credentialValidator;
    private final UserSessionRevoker userSessionRevoker;
    private final SessionFinder sessionFinder;
    private final RefreshTokenDeleter refreshTokenDeleter;
    private final PatchValidator patchValidator;
    private final AuthUser authUser;

    @Transactional
    public MeResponse update(MePatchRequest mePatchRequest) {
        patchValidator.validate(mePatchRequest);
        UserEntity userEntity = userFinder.findByIdOrThrow(authUser.getId());
        CredentialEntity credentialEntity = credentialFinder
                .findByUserEntityAndCredentialProviderOrThrow(userEntity, CredentialProvider.LOCAL);
        credentialValidator.validateCurrentPassword(credentialEntity, mePatchRequest.currentPassword());
        if (mePatchRequest.username() != null) {
            userEntity.updateUsername(mePatchRequest.username());
        }
        if (mePatchRequest.email() != null) {
            userEntity.updateEmail(mePatchRequest.email());
        }
        if (mePatchRequest.password() != null) {
            credentialUpdater.updatePassword(credentialEntity, mePatchRequest.password());
            userSessionRevoker.revokeAll(userEntity);
        }
        userEntity.updateUpdatedBy(authUser.getId());
        return MeResponse.fromEntity(userEntity);
    }

    @Transactional
    public MeResponse replace(MeRequest meRequest) {
        UserEntity userEntity = userFinder.findByIdOrThrow(authUser.getId());
        CredentialEntity credentialEntity = credentialFinder
                .findByUserEntityAndCredentialProviderOrThrow(userEntity, CredentialProvider.LOCAL);
        credentialValidator.validateCurrentPassword(credentialEntity, meRequest.currentPassword());
        userEntity.updateUsername(meRequest.username());
        userEntity.updateEmail(meRequest.email());
        credentialUpdater.updatePassword(credentialEntity, meRequest.password());
        userSessionRevoker.revokeAll(userEntity);
        userEntity.updateUpdatedBy(authUser.getId());
        return MeResponse.fromEntity(userEntity);
    }

    public MeResponse findMe() {
        UserEntity userEntity = userFinder.findByIdOrThrow(authUser.getId());
        return MeResponse.fromEntity(userEntity);
    }

    public List<SessionResponse> findActiveSessions() {
        return sessionFinder.findAllActiveSessions(authUser.getId())
                .stream()
                .map(SessionResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void revokeSession(Long sessionId) {
        SessionEntity sessionEntity = sessionFinder.findByIdAndUserEntity_IdOrThrow(sessionId, authUser.getId());
        sessionEntity.revoke();
        refreshTokenDeleter.deleteBySessionEntity(sessionEntity);
    }
}
