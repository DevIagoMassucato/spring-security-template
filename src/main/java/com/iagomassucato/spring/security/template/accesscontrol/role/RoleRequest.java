package com.iagomassucato.spring.security.template.accesscontrol.role;

import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
public class RoleRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotEmpty(message = "permissionIds is required")
    private Set<Long> permissionIds;
}
