package com.iagomassucato.springsecuritytemplate.accesscontrol.role;

import com.iagomassucato.springsecuritytemplate.shared.PatchRequest;
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