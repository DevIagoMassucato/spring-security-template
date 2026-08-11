package com.iagomassucato.spring.security.template.accesscontrol.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record RoleRequest(
        @NotBlank(message = "name is required")
        String name,
        @NotEmpty(message = "permissionIds is required")
        Set<Long> permissionIds
) {
}
