package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import java.util.Set;
import java.util.stream.Collectors;

public record UserResponse(
        Long id,
        String username,
        String email,
        Set<String> roles
) {
    public static UserResponse fromEntity(UserEntity userEntity) {
        Set<String> roles = userEntity.getRoleEntitySet()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
        return new UserResponse(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                roles
        );
    }
}
