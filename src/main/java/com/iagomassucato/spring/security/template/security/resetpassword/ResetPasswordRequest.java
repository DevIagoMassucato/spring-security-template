package com.iagomassucato.spring.security.template.security.resetpassword;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @NotBlank(message = "username is required")
        String username
) {
}
