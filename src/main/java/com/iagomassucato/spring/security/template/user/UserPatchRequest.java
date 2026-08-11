package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.shared.PatchRequest;
import java.util.Set;

public record UserPatchRequest(
        String username,
        String password,
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
