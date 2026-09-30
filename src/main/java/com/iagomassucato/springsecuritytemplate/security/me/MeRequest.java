package com.iagomassucato.springsecuritytemplate.security.me;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MeRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        @Size(min = 1, max = 15, message = "password can have a maximum of 15 characters")
        @Pattern(regexp = "^\\S+$", message = "password must not contain spaces")
        String password,
        @NotBlank(message = "email is required")
        @Email(message = "email is invalid")
        String email,
        @NotBlank(message = "currentPassword is required")
        String currentPassword
) {
}
