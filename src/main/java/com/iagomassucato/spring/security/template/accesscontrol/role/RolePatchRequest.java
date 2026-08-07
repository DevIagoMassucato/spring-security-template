package com.iagomassucato.spring.security.template.accesscontrol.role;

import com.iagomassucato.spring.security.template.shared.PatchRequest;
import lombok.Getter;
import java.util.Set;

@Getter
public class RolePatchRequest implements PatchRequest {
    private String name;
    private Set<Long> permissionIds;

    @Override
    public boolean hasFieldsToUpdate() {
        return hasValue(name) || hasPermissionIds(permissionIds);
    }

    private boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }

    private boolean hasPermissionIds(Set<Long> permissionIds) {
        return permissionIds != null && !permissionIds.isEmpty();
    }
}