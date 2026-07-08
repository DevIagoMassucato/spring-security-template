package com.iagomassucato.spring.security.template.security.userdetails;

import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionEntity;
import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import com.iagomassucato.spring.security.template.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.HashSet;
import java.util.Set;

public final class AuthorityMapper {
    public static Set<GrantedAuthority> map(UserEntity userEntity) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RoleEntity roleEntity : userEntity.getRoleEntitySet()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getName()));
            for (PermissionEntity permissionEntity : roleEntity.getPermissionEntitySet()) {
                authorities.add(new SimpleGrantedAuthority(permissionEntity.getName()));
            }
        }
        return authorities;
    }
}
