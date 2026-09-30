package com.iagomassucato.springsecuritytemplate.security.userdetails;

import com.iagomassucato.springsecuritytemplate.security.authority.AuthorityMapper;
import com.iagomassucato.springsecuritytemplate.security.credential.CredentialEntity;
import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private final UserEntity userEntity;
    private final CredentialEntity credentialEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityMapper.fromUserEntity(userEntity);
    }

    @Override
    public String getPassword() {
        return credentialEntity.getPasswordHash();
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
