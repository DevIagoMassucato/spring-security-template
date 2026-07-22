package com.iagomassucato.spring.security.template.security.auth;

import com.iagomassucato.spring.security.template.security.jwt.JwtService;
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
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                );
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            refreshTokenService.create(refreshToken, userDetails.getUserEntity());
            return new AuthResponse(accessToken, refreshToken);
    }
}
