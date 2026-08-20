package com.iagomassucato.spring.security.template.security.session;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.time.Duration;

@ConfigurationProperties(prefix = "security.session")
@Getter
@Setter
public class SessionProperties {
    private Duration expiration;
}
