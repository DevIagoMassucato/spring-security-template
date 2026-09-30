package com.iagomassucato.spring.security.template.security.me;

import com.iagomassucato.spring.security.template.shared.PatchRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MePatchRequest(
        String username,
        @Size(min = 1, max = 15, message = "password can have a maximum of 15 characters")
        @Pattern(regexp = "^\\S+$", message = "password must not contain spaces")
        String password,
        @Email(message = "email is invalid")
        String email,
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
