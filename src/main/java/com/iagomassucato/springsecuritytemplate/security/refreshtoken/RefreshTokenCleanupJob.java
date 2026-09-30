package com.iagomassucato.springsecuritytemplate.security.refreshtoken;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class RefreshTokenCleanupJob {

    private final RefreshTokenDeleter refreshTokenDeleter;

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void deleteExpiredTokens() {
        refreshTokenDeleter.deleteByExpiresAtBefore(Instant.now());
    }
}
