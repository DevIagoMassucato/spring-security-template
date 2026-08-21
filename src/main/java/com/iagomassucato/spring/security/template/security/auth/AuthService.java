package com.iagomassucato.spring.security.template.security.auth;

import com.iagomassucato.spring.security.template.security.jwt.JwtService;
import com.iagomassucato.spring.security.template.security.jwt.JwtToken;
import com.iagomassucato.spring.security.template.security.refreshtoken.RefreshTokenCreator;
import com.iagomassucato.spring.security.template.security.session.SessionEntity;
import com.iagomassucato.spring.security.template.security.session.SessionCreator;
import com.iagomassucato.spring.security.template.security.userdetails.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenCreator refreshTokenCreator;
    private final SessionCreator sessionCreator;

    public AuthResponse login(AuthRequest authRequest, HttpServletRequest httpServletRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );
        UserDetailsImpl userDetailsImpl = validatePrincipal(authentication);
        String ipAddress = httpServletRequest.getRemoteAddr();
        String userAgent = httpServletRequest.getHeader("User-Agent");
        SessionEntity sessionEntity = sessionCreator.create(userDetailsImpl.getUserEntity(), ipAddress, userAgent);
        Long userId = userDetailsImpl.getUserEntity().getId();
        Long sessionId = sessionEntity.getId();
        JwtToken accessToken = jwtService.generateAccessToken(userId, sessionId);
        JwtToken refreshToken = jwtService.generateRefreshToken(userId, sessionId);
        refreshTokenCreator.create(refreshToken, sessionEntity);
        return new AuthResponse(accessToken.token(), refreshToken.token());
    }

    private UserDetailsImpl validatePrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl userDetailsImpl) {
            return userDetailsImpl;
        }
        throw new IllegalStateException("authenticated principal is not a UserDetailsImpl");
    }
}
