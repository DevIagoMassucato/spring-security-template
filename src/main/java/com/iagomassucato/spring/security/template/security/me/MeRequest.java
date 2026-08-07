package com.iagomassucato.spring.security.template.security.me;

import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Getter
public class MeRequest {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "currentPassword is required")
    private String currentPassword;
}
