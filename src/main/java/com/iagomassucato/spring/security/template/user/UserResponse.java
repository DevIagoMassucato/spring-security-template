package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class UserResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final Set<String> roles;

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
