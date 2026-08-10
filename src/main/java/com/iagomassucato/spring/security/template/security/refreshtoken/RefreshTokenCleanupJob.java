package com.iagomassucato.spring.security.template.security.refreshtoken;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RefreshTokenCleanupJob {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteByExpirationDateBefore(LocalDateTime.now());
    }
}
