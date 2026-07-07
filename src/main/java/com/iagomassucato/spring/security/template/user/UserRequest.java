package com.iagomassucato.spring.security.template.user;

import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class UserRequest {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    @NotNull(message = "roleId is required")
    private Long roleId;
}
