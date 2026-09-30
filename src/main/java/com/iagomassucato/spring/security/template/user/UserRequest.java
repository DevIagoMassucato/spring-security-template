package com.iagomassucato.spring.security.template.user;

import jakarta.validation.constraints.*;
import java.util.Set;

public record UserRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        @Size(min = 1, max = 15, message = "password can have a maximum of 15 characters")
        @Pattern(regexp = "^\\S+$", message = "password must not contain spaces")
        String password,
        @NotBlank(message = "email is required")
        @Email(message = "email is invalid")
        String email,
        @NotEmpty(message = "roleIds is required")
        Set<Long> roleIds
) {
}
