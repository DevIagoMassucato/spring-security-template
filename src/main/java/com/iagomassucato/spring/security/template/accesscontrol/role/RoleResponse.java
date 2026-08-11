package com.iagomassucato.spring.security.template.accesscontrol.role;

import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionEntity;
import java.util.Set;
import java.util.stream.Collectors;

public record RoleResponse(
        Long id,
        String name,
        Set<String> permissions
) {
    public static RoleResponse fromEntity(RoleEntity roleEntity){
        return new RoleResponse(
                roleEntity.getId(),
                roleEntity.getName(),
                roleEntity.getPermissionEntitySet()
                        .stream()
                        .map(PermissionEntity::getName)
                        .collect(Collectors.toSet())
        );
    }
}
