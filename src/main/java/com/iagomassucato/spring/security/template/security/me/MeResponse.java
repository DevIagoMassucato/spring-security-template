package com.iagomassucato.spring.security.template.security.me;

import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import com.iagomassucato.spring.security.template.user.UserEntity;
import java.util.Set;
import java.util.stream.Collectors;

public record MeResponse(
        String username,
        String email,
        Set<String> roles
) {
    public static MeResponse fromEntity(UserEntity userEntity) {
        Set<String> roles = userEntity.getRoleEntitySet()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
        return new MeResponse(
                userEntity.getUsername(),
                userEntity.getEmail(),
                roles
        );
    }
}
