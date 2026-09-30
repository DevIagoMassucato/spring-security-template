package com.iagomassucato.spring.security.template.security.jwt;

import com.iagomassucato.spring.security.template.security.authority.AuthorityFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;

@Component
public class JwtAuthorityConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList(JwtClaimsConstants.ROLES);
        List<String> permissions = jwt.getClaimAsStringList(JwtClaimsConstants.PERMISSIONS);
        Set<GrantedAuthority> grantedAuthoritySet = AuthorityFactory.create(roles, permissions);
        return new JwtAuthenticationToken(jwt, grantedAuthoritySet);
    }
}

