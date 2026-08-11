package com.iagomassucato.spring.security.template.security.me;

import com.iagomassucato.spring.security.template.shared.PatchRequest;
import jakarta.validation.constraints.NotBlank;

public record MePatchRequest(
        String username,
        String email,
        String password,
        @NotBlank(message = "currentPassword is required")
        String currentPassword
) implements PatchRequest{

    @Override
    public boolean hasFieldsToUpdate() {
        return hasValue(username) || hasValue(email) || hasValue(password);
    }

    private boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }
}
