package com.iagomassucato.springsecuritytemplate.security.resetpassword;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ResetPasswordCleanupJob {

    private final ResetPasswordDeleter resetPasswordDeleter;

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void deleteExpiredCodes() {
        resetPasswordDeleter.deleteByExpiresAtBefore(Instant.now());
    }
}
