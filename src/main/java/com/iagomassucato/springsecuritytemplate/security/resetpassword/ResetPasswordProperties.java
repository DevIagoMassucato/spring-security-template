package com.iagomassucato.springsecuritytemplate.security.resetpassword;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.time.Duration;

@ConfigurationProperties(prefix = "security.reset-password")
@Getter
@Setter
public class ResetPasswordProperties {
    private Duration expiration;
}
