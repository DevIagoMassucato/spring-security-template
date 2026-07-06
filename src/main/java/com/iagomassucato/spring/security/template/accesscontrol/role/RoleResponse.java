package com.iagomassucato.spring.security.template.accesscontrol.role;

import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;
    private Set<String> permissions;

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
