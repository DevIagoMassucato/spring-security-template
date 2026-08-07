package com.iagomassucato.spring.security.template.security.auth;

import com.iagomassucato.spring.security.template.security.jwt.JwtService;
import com.iagomassucato.spring.security.template.security.jwt.JwtToken;
import com.iagomassucato.spring.security.template.security.refreshtoken.RefreshTokenService;
import com.iagomassucato.spring.security.template.security.userdetails.UserDetailsImpl;
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
    private final RefreshTokenService refreshTokenService;

    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        JwtToken accessToken = jwtService.generateAccessToken(userDetailsImpl.getUserEntity());
        JwtToken refreshToken = jwtService.generateRefreshToken(userDetailsImpl.getUserEntity());
        refreshTokenService.create(refreshToken, userDetailsImpl.getUserEntity());
        return new AuthResponse(accessToken.getToken(), refreshToken.getToken());
    }

}
