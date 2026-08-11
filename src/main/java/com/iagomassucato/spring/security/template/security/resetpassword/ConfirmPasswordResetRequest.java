package com.iagomassucato.spring.security.template.security.resetpassword;

import jakarta.validation.constraints.NotBlank;

public record ConfirmPasswordResetRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "code is required")
        String code,
        @NotBlank(message = "new password is required")
        String newPassword
) {
}
