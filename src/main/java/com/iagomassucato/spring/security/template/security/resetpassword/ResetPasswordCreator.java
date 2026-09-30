package com.iagomassucato.spring.security.template.security.resetpassword;

import com.iagomassucato.spring.security.template.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ResetPasswordCreator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final ResetPasswordRepository resetPasswordRepository;
    private final ResetPasswordProperties resetPasswordProperties;

    public ResetPasswordEntity create(UserEntity userEntity) {
        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plus(resetPasswordProperties.getExpiration());
        String code = generateCode();
        ResetPasswordEntity resetPasswordEntity = ResetPasswordEntity.create(
                userEntity,
                code,
                createdAt,
                expiresAt
        );
        return resetPasswordRepository.save(resetPasswordEntity);
    }

    private String generateCode() {
        return String.valueOf(100000 + RANDOM.nextInt(900000));
    }
}
