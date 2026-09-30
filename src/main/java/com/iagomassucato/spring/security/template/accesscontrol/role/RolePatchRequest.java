package com.iagomassucato.spring.security.template.accesscontrol.role;

import com.iagomassucato.spring.security.template.shared.PatchRequest;
import java.util.Set;

public record RolePatchRequest(
    String name,
    Set<Long> permissionIds
) implements PatchRequest {

    @Override
    public boolean hasFieldsToUpdate() {
        return hasValue(name) || hasPermissionIds(permissionIds);
    }

    private boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }

    private boolean hasPermissionIds(Set<Long> permissionIds) {
        return permissionIds != null;
    }
}