package com.iagomassucato.spring.security.template.accesscontrol.permission;

public record PermissionResponse(
        Long id,
        String name
) {
    public static PermissionResponse fromEntity (PermissionEntity permissionEntity){
        return new PermissionResponse(
                permissionEntity.getId(),
                permissionEntity.getName()
        );
    }
}