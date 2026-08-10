package com.iagomassucato.spring.security.template.accesscontrol.permission;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PermissionRequest {
    @NotBlank(message = "name is required")
    private String name;
}
