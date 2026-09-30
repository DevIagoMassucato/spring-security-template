package com.iagomassucato.spring.security.template.security.jwt;

import com.iagomassucato.spring.security.template.security.authority.AuthorityConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtAuthorityMapper {

    public static Set<String> toRoles(Collection<? extends GrantedAuthority> grantedAuthority) {
        return grantedAuthority.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith(AuthorityConstants.ROLE_PREFIX))
                .map(authority -> authority.substring(AuthorityConstants.ROLE_PREFIX.length()))
                .collect(Collectors.toSet());
    }

    public static Set<String> toPermissions(Collection<? extends GrantedAuthority> grantedAuthority) {
        return grantedAuthority.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> !authority.startsWith(AuthorityConstants.ROLE_PREFIX))
                .collect(Collectors.toSet());
    }
}