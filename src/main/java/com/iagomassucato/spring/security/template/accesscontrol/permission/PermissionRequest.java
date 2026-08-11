package com.iagomassucato.spring.security.template.accesscontrol.permission;

import jakarta.validation.constraints.NotBlank;

public record PermissionRequest(
        @NotBlank(message = "name is required")
        String name
) {
}
