package com.iagomassucato.spring.security.template.accesscontrol.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import java.util.Set;

@Getter
public class RoleRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotEmpty(message = "permissionIds is required")
    private Set<Long> permissionIds;
}
