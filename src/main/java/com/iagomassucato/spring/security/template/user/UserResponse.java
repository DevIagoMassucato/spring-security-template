package com.iagomassucato.spring.security.template.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iagomassucato.spring.security.template.accesscontrol.role.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse {

    private Long id;
    private String username;

    @JsonProperty("role")
    private RoleResponse roleResponse;

    public static UserResponse fromEntity(UserEntity userEntity){
        return new UserResponse(
                userEntity.getId(),
                userEntity.getUsername(),
                RoleResponse.fromEntity(userEntity.getRoleEntity())
        );
    }
}
