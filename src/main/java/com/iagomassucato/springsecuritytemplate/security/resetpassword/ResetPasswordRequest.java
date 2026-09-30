package com.iagomassucato.springsecuritytemplate.security.resetpassword;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @NotBlank(message = "username is required")
        String username
) {
}
