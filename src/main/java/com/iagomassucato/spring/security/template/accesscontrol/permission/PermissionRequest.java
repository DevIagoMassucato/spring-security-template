package com.iagomassucato.spring.security.template.accesscontrol.permission;

import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Getter
public class PermissionRequest {
    @NotBlank(message = "name is required")
    private String name;
}
