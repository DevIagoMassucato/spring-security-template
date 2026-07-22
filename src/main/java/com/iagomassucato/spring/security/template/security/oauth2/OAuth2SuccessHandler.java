package com.iagomassucato.spring.security.template.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iagomassucato.spring.security.template.security.auth.AuthResponse;
import com.iagomassucato.spring.security.template.security.jwt.JwtService;
import com.iagomassucato.spring.security.template.security.refreshtoken.RefreshTokenService;
import com.iagomassucato.spring.security.template.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2AuthenticationService oAuth2AuthenticationService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        UserDetailsImpl userDetails = oAuth2AuthenticationService.authenticate(authentication);
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        refreshTokenService.create(refreshToken, userDetails.getUserEntity());
        AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(response.getOutputStream(), authResponse);
    }
}
