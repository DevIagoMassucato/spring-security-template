package com.iagomassucato.springsecuritytemplate.accesscontrol.permission;

import jakarta.validation.constraints.NotBlank;

public record PermissionRequest(
        @NotBlank(message = "name is required")
        String name
) {
}
