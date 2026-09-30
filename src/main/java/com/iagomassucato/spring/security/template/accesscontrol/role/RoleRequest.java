package com.iagomassucato.spring.security.template.accesscontrol.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record RoleRequest(
        @NotBlank(message = "name is required")
        String name,
        @NotNull(message = "permissionIds cannot be null")
        Set<Long> permissionIds
) {
}

/*permissionIds must either be empty or contain a value
example:
"permissionIds": []
"permissionIds": [1, 2, 3]
 */