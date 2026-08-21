package com.iagomassucato.spring.security.template.security.auth;

import com.iagomassucato.spring.security.template.security.userdetails.UserDetailsImpl;
import com.iagomassucato.spring.security.template.user.UserEntity;
import com.iagomassucato.spring.security.template.user.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUser {

    private final UserFinder userFinder;

    public UserEntity get() {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userFinder.findByIdOrThrow(
                userDetails.getUserEntity().getId()
        );
    }
}
