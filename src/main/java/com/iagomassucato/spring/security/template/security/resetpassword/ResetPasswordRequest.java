package com.iagomassucato.spring.security.template.security.resetpassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "username is required")
    private String username;
}
