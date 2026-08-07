package com.iagomassucato.spring.security.template.security.resetpassword;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "username is required")
    private String username;
}
