package com.iagomassucato.spring.security.template.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.time.Duration;

@ConfigurationProperties(prefix = "security.jwt")
@Getter
@Setter
public class JwtProperties {
    private String secretKey;
    private Duration accessTokenExpiration;
    private Duration refreshTokenExpiration;
}
