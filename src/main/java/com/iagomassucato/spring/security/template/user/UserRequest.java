package com.iagomassucato.spring.security.template.user;

import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
public class UserRequest {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    @NotEmpty(message = "roleIds is required")
    private Set<Long> roleIds;
}
