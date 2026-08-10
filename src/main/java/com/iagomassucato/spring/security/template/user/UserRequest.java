package com.iagomassucato.spring.security.template.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import java.util.Set;

@Getter
public class UserRequest {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "email is required")
    private String email;
    @NotEmpty(message = "roleIds is required")
    private Set<Long> roleIds;
}
