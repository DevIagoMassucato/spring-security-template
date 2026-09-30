package com.iagomassucato.spring.security.template.security.resetpassword;

import com.iagomassucato.spring.security.template.security.credential.CredentialEntity;
import com.iagomassucato.spring.security.template.security.credential.CredentialFinder;
import com.iagomassucato.spring.security.template.security.credential.CredentialProvider;
import com.iagomassucato.spring.security.template.security.credential.CredentialUpdater;
import com.iagomassucato.spring.security.template.user.UserEntity;
import com.iagomassucato.spring.security.template.user.UserFinder;
import com.iagomassucato.spring.security.template.user.UserSessionRevoker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordService {

    private final ResetPasswordRepository resetPasswordRepository;
    private final ResetPasswordCreator resetPasswordCreator;
    private final PasswordRecoveryNotifier passwordRecoveryNotifier;
    private final ResetPasswordFinder resetPasswordFinder;
    private final UserFinder userFinder;
    private final CredentialFinder credentialFinder;
    private final CredentialUpdater credentialUpdater;
    private final UserSessionRevoker userSessionRevoker;



    @Transactional
    public ResetPasswordResponse forgotPassword(ResetPasswordRequest resetPasswordRequest) {
        UserEntity userEntity = userFinder.findByUsernameOptional(resetPasswordRequest.username()).orElse(null);
        if (userEntity != null) {
            resetPasswordRepository.deleteByUserEntityAndUsedFalse(userEntity);
            ResetPasswordEntity resetPasswordEntity = resetPasswordCreator.create(userEntity);
            passwordRecoveryNotifier.sendRecoveryCode(userEntity, resetPasswordEntity.getCode());
        }
        return new ResetPasswordResponse("recovery code sent successfully");
    }

    @Transactional
    public void resetPassword(ConfirmPasswordResetRequest confirmPasswordResetRequest) {
        UserEntity userEntity = userFinder.findByUsernameOrThrow(confirmPasswordResetRequest.username());
        ResetPasswordEntity resetPasswordEntity = resetPasswordFinder
                .findByUserEntityAndCode(userEntity, confirmPasswordResetRequest.code());
        resetPasswordEntity.validateCodeStatus();
        CredentialEntity credentialEntity = credentialFinder
                .findByUserEntityAndCredentialProviderOrThrow(userEntity, CredentialProvider.LOCAL);
        credentialUpdater.updatePassword(credentialEntity, confirmPasswordResetRequest.newPassword());
        resetPasswordEntity.markAsUsed();
        userSessionRevoker.revokeAll(userEntity);
        userEntity.updateUpdatedBy(userEntity.getId());
    }
}
