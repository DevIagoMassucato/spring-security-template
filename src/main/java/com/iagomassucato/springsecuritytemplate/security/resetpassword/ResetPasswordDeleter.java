package com.iagomassucato.springsecuritytemplate.security.resetpassword;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ResetPasswordDeleter {

    private final ResetPasswordRepository resetPasswordRepository;

    public void deleteByExpiresAtBefore(Instant dateTime) {
        resetPasswordRepository.deleteByExpiresAtBefore(dateTime);
    }
}
