package com.iagomassucato.spring.security.template.security.refreshtoken;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RefreshTokenFinder {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenEntity findByTokenOrThrow(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new NoSuchElementException("refresh token not found"));
    }
}
