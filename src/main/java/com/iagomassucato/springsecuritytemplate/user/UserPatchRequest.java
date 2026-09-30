package com.iagomassucato.springsecuritytemplate.user;

import com.iagomassucato.springsecuritytemplate.shared.PatchRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserPatchRequest(
        String username,
        @Size(min = 1, max = 15, message = "password can have a maximum of 15 characters")
        @Pattern(regexp = "^\\S+$", message = "password must not contain spaces")
        String password,
        @Email(message = "email is invalid")
        String email,
        Set<Long> roleIds
) implements PatchRequest {

    @Override
    public boolean hasFieldsToUpdate() {
        return hasValue(username) || hasValue(email) || hasValue(password) || hasRoleIds(roleIds);
    }

    private boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }

    private boolean hasRoleIds(Set<Long> roleIds) {
        return roleIds != null && !roleIds.isEmpty();
    }
}
