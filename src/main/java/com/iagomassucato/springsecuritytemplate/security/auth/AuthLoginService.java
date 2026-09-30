package com.iagomassucato.springsecuritytemplate.security.auth;

import com.iagomassucato.springsecuritytemplate.security.jwt.JwtAuthorityMapper;
import com.iagomassucato.springsecuritytemplate.security.jwt.JwtGenerator;
import com.iagomassucato.springsecuritytemplate.security.jwt.JwtToken;
import com.iagomassucato.springsecuritytemplate.security.refreshtoken.RefreshTokenCreator;
import com.iagomassucato.springsecuritytemplate.security.session.SessionCreator;
import com.iagomassucato.springsecuritytemplate.security.session.SessionEntity;
import com.iagomassucato.springsecuritytemplate.security.userdetails.UserDetailsImpl;
import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthLoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenCreator refreshTokenCreator;
    private final SessionCreator sessionCreator;

    @Transactional
    public AuthResponse login(AuthRequest authRequest, HttpServletRequest httpServletRequest) {
        Authentication authentication = authenticate(authRequest);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        UserEntity userEntity = userDetailsImpl.getUserEntity();
        SessionEntity sessionEntity = sessionCreator.create(
                userEntity,
                httpServletRequest.getRemoteAddr(),
                httpServletRequest.getHeader("User-Agent")
        );
        Collection<? extends GrantedAuthority> grantedAuthority = authentication.getAuthorities();
        Set<String> roles = JwtAuthorityMapper.toRoles(grantedAuthority);
        Set<String> permissions = JwtAuthorityMapper.toPermissions(grantedAuthority);
        JwtToken accessToken = jwtGenerator.generateAccessToken(
                userEntity.getId(),
                sessionEntity.getId(),
                roles,
                permissions
        );
        JwtToken refreshToken = jwtGenerator.generateRefreshToken(
                userEntity.getId(),
                sessionEntity.getId()
        );
        refreshTokenCreator.create(refreshToken, sessionEntity);
        return new AuthResponse(accessToken.token(), refreshToken.token());
    }

    private Authentication authenticate(AuthRequest authRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.username(),
                        authRequest.password()
                )
        );
    }
}
