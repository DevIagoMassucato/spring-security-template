package com.iagomassucato.springsecuritytemplate.security.resetpassword;

import com.iagomassucato.springsecuritytemplate.client.NotificationClient;
import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordRecoveryNotifier {

    private final NotificationClient notificationClient;

    public void sendRecoveryCode(UserEntity userEntity, String code) {
        notificationClient.sendEmail(
                userEntity.getEmail(),
                "Password recovery",
                "Your recovery code is: " + code
        );
    }
}
