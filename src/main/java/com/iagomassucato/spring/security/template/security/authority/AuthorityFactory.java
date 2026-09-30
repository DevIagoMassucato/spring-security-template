package com.iagomassucato.spring.security.template.security.authority;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthorityFactory {

    public static Set<GrantedAuthority> create(Collection<String> roles, Collection<String> permissions) {
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        addAuthorities(roles, role -> AuthorityConstants.ROLE_PREFIX + role, grantedAuthoritySet);
        addAuthorities(permissions, UnaryOperator.identity(), grantedAuthoritySet);
        return grantedAuthoritySet;
    }

    private static void addAuthorities(
            Collection<String> authorities,
            UnaryOperator<String> prefix,
            Set<GrantedAuthority> grantedAuthoritySet
    ) {
        if (authorities == null || authorities.isEmpty()) {
            return;
        }
        authorities.stream()
                .filter(authority -> authority != null && !authority.isBlank())
                .map(String::trim)
                .map(prefix)
                .map(SimpleGrantedAuthority::new)
                .forEach(grantedAuthoritySet::add);
    }
}
