package com.iagomassucato.springsecuritytemplate.accesscontrol.permission;

import java.time.Instant;

public record PermissionResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
    public static PermissionResponse fromEntity (PermissionEntity permissionEntity){
        return new PermissionResponse(
                permissionEntity.getId(),
                permissionEntity.getName(),
                permissionEntity.getCreatedAt(),
                permissionEntity.getUpdatedAt()
        );
    }
}