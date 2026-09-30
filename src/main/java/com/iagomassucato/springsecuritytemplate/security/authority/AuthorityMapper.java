package com.iagomassucato.springsecuritytemplate.security.authority;

import com.iagomassucato.springsecuritytemplate.accesscontrol.permission.PermissionEntity;
import com.iagomassucato.springsecuritytemplate.accesscontrol.role.RoleEntity;
import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthorityMapper {

    public static Set<GrantedAuthority> fromUserEntity(UserEntity userEntity) {
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        for (RoleEntity roleEntity : userEntity.getRoleEntitySet()) {
            roles.add(roleEntity.getName());
            for (PermissionEntity permissionEntity : roleEntity.getPermissionEntitySet()) {
                permissions.add(permissionEntity.getName());
            }
        }
        return AuthorityFactory.create(roles, permissions);
    }
}
