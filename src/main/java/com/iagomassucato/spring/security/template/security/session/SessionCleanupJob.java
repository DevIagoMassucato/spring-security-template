package com.iagomassucato.spring.security.template.security.session;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class SessionCleanupJob {

    private final SessionDeleter sessionDeleter;

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void deleteExpiredAndRevokedSessions() {
        Instant now = Instant.now();
        sessionDeleter.deleteByExpiresAtBefore(now);
    }
}
