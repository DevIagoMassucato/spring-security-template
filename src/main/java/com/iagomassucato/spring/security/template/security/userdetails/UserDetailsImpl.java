package com.iagomassucato.spring.security.template.security.userdetails;

import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionEntity;
import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import com.iagomassucato.spring.security.template.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RoleEntity roleEntity : userEntity.getRoleEntitySet()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getName()));
            for (PermissionEntity permissionEntity : roleEntity.getPermissionEntitySet()) {
                authorities.add(new SimpleGrantedAuthority(permissionEntity.getName()));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
