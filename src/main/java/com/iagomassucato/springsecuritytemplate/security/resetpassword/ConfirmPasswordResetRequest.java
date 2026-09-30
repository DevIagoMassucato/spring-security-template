package com.iagomassucato.springsecuritytemplate.security.resetpassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ConfirmPasswordResetRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "code is required")
        String code,
        @NotBlank(message = "newPassword is required")
        @Size(min = 1, max = 15, message = "newPassword can have a maximum of 15 characters")
        @Pattern(regexp = "^\\S+$", message = "newPassword must not contain spaces")
        String newPassword
) {
}
