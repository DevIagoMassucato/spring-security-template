package com.iagomassucato.spring.security.template.security.resetpassword;

import com.iagomassucato.spring.security.template.client.NotificationClient;
import com.iagomassucato.spring.security.template.user.UserEntity;
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
