package com.iagomassucato.springsecuritytemplate.accesscontrol.role;

import com.iagomassucato.springsecuritytemplate.accesscontrol.permission.PermissionEntity;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record RoleResponse(
        Long id,
        String name,
        Set<String> permissions,
        Instant createdAt,
        Instant updatedAt
) {
    public static RoleResponse fromEntity(RoleEntity roleEntity) {
        Set<String> permissions = roleEntity.getPermissionEntitySet()
                .stream()
                .map(PermissionEntity::getName)
                .collect(Collectors.toSet());
        return new RoleResponse(
                roleEntity.getId(),
                roleEntity.getName(),
                permissions,
                roleEntity.getCreatedAt(),
                roleEntity.getUpdatedAt()
        );
    }
}
