package com.iagomassucato.spring.security.template.accesscontrol.role;

import lombok.Getter;
import java.util.Set;

@Getter
public class RolePatchRequest {
    private String name;
    private Set<Long> permissionIds;
}