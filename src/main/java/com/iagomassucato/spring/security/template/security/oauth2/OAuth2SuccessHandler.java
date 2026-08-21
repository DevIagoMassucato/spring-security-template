package com.iagomassucato.spring.security.template.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iagomassucato.spring.security.template.security.auth.AuthResponse;
import com.iagomassucato.spring.security.template.security.jwt.JwtService;
import com.iagomassucato.spring.security.template.security.jwt.JwtToken;
import com.iagomassucato.spring.security.template.security.refreshtoken.RefreshTokenCreator;
import com.iagomassucato.spring.security.template.security.session.SessionCreator;
import com.iagomassucato.spring.security.template.security.session.SessionEntity;
import com.iagomassucato.spring.security.template.security.userdetails.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2AuthenticationService oAuth2AuthenticationService;
    private final SessionCreator sessionCreator;
    private final JwtService jwtService;
    private final RefreshTokenCreator refreshTokenCreator;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        UserDetailsImpl userDetailsImpl = oAuth2AuthenticationService.authenticate(authentication);
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        SessionEntity sessionEntity = sessionCreator.create(userDetailsImpl.getUserEntity(), ipAddress, userAgent);
        Long userId = userDetailsImpl.getUserEntity().getId();
        Long sessionId = sessionEntity.getId();
        JwtToken accessToken = jwtService.generateAccessToken(userId, sessionId);
        JwtToken refreshToken = jwtService.generateRefreshToken(userId, sessionId);
        refreshTokenCreator.create(refreshToken, sessionEntity);
        AuthResponse authResponse = new AuthResponse(accessToken.token(), refreshToken.token());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(response.getOutputStream(), authResponse);
    }
}
