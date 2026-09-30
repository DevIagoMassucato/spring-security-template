package com.iagomassucato.springsecuritytemplate.security.me;

import com.iagomassucato.springsecuritytemplate.accesscontrol.role.RoleEntity;
import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record MeResponse(
        String username,
        String email,
        Set<String> roles,
        Instant createdAt,
        Instant updatedAt,
        Long createdBy,
        Long updatedBy
) {
    public static MeResponse fromEntity(UserEntity userEntity) {
        Set<String> roles = userEntity.getRoleEntitySet()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
        return new MeResponse(
                userEntity.getUsername(),
                userEntity.getEmail(),
                roles,
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt(),
                userEntity.getCreatedBy(),
                userEntity.getUpdatedBy()
        );
    }
}
