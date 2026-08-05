package com.iagomassucato.spring.security.template.accesscontrol.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PermissionResponse {
    private final Long id;
    private final String name;

    public static PermissionResponse fromEntity (PermissionEntity permissionEntity){
        return new PermissionResponse(
                permissionEntity.getId(),
                permissionEntity.getName()
        );
    }
}