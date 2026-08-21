package com.iagomassucato.spring.security.template.security.resetpassword;

import com.iagomassucato.spring.security.template.client.NotificationClient;
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
import java.security.SecureRandom;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ResetPasswordService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final ResetPasswordRepository resetPasswordRepository;
    private final NotificationClient notificationClient;
    private final UserFinder userFinder;
    private final CredentialFinder credentialFinder;
    private final CredentialUpdater credentialUpdater;
    private final UserSessionRevoker userSessionRevoker;



    @Transactional
    public ResetPasswordResponse forgotPassword(ResetPasswordRequest resetPasswordRequest) {
        UserEntity userEntity = userFinder.findByUsernameOptional(resetPasswordRequest.username()).orElse(null);
        if (userEntity != null) {
            resetPasswordRepository.deleteByUserEntityAndUsedFalse(userEntity);
            String code = generateCode();
            ResetPasswordEntity resetPasswordEntity = ResetPasswordEntity.create(userEntity, code);
            resetPasswordRepository.save(resetPasswordEntity);
            notificationClient.sendEmail(
                    userEntity.getEmail(),
                    "Password recovery",
                    "Your recovery code is: " + code
            );
        }
        return new ResetPasswordResponse("recovery code sent successfully");
    }

    @Transactional
    public void resetPassword(ConfirmPasswordResetRequest confirmPasswordResetRequest) {
        UserEntity userEntity = userFinder.findByUsernameOrThrow(confirmPasswordResetRequest.username());
        ResetPasswordEntity resetPasswordEntity = resetPasswordRepository
                .findByUserEntityAndCode(userEntity, confirmPasswordResetRequest.code())
                .orElseThrow(() -> new NoSuchElementException("invalid code"));
        resetPasswordEntity.validateCodeStatus();
        CredentialEntity credentialEntity = credentialFinder
                .findByUserEntityAndCredentialProviderOrThrow(userEntity, CredentialProvider.LOCAL);
        credentialUpdater.updatePassword(credentialEntity, confirmPasswordResetRequest.newPassword());
        resetPasswordEntity.markAsUsed();
        resetPasswordRepository.save(resetPasswordEntity);
        userSessionRevoker.revokeAll(userEntity);
    }

    private String generateCode() {
        return String.valueOf(100000 + RANDOM.nextInt(900000));
    }
}
