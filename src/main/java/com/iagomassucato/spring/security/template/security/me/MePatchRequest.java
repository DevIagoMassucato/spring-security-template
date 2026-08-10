package com.iagomassucato.spring.security.template.security.me;

import com.iagomassucato.spring.security.template.shared.PatchRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MePatchRequest implements PatchRequest {
    private String username;
    private String email;
    private String password;
    @NotBlank(message = "currentPassword is required")
    private String currentPassword;

    @Override
    public boolean hasFieldsToUpdate() {
        return hasValue(username) || hasValue(email) || hasValue(password);
    }

    private boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }
}
