package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.shared.PatchRequest;
import lombok.Getter;
import java.util.Set;

@Getter
public class UserPatchRequest implements PatchRequest {
    private String username;
    private String password;
    private String email;
    private Set<Long> roleIds;

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
