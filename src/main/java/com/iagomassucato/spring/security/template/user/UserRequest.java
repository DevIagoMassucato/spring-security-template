package com.iagomassucato.spring.security.template.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record UserRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        String password,
        @NotBlank(message = "email is required")
        String email,
        @NotEmpty(message = "roleIds is required")
        Set<Long> roleIds
) {
}
