package com.iagomassucato.spring.security.template.security.refreshtoken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {
    private String refreshToken;
}
