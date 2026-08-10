package com.iagomassucato.spring.security.template.security.resetpassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmPasswordResetRequest {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "code is required")
    private String code;
    @NotBlank(message = "new password is required")
    private String newPassword;
}
