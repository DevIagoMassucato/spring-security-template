package com.iagomassucato.spring.security.template.config;

import com.iagomassucato.spring.security.template.security.jwt.JwtAccessTokenValidator;
import com.iagomassucato.spring.security.template.security.jwt.JwtProperties;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import javax.crypto.SecretKey;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProperties jwtProperties;
    private final JwtAccessTokenValidator jwtAccessTokenValidator;

    @Bean
    public SecretKey jwtSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey jwtSecretKey) {
        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(jwtSecretKey).build();
        OAuth2TokenValidator<Jwt> defaultValidators = JwtValidators.createDefault();
        OAuth2TokenValidator<Jwt> issuerValidator = new JwtIssuerValidator(jwtProperties.getIssuer());
        OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator(jwtProperties.getAudience());
        OAuth2TokenValidator<Jwt> combinedValidator = new DelegatingOAuth2TokenValidator<>(
                defaultValidators,
                issuerValidator,
                audienceValidator,
                jwtAccessTokenValidator
        );
        nimbusJwtDecoder.setJwtValidator(combinedValidator);
        return nimbusJwtDecoder;
    }
}
