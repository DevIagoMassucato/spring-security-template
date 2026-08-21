package com.iagomassucato.spring.security.template.security.refreshtoken;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class RefreshTokenFinder {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenEntity findByTokenIdOrThrow(String tokenId) {
        return refreshTokenRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new NoSuchElementException("refresh token not found"));
    }
}
