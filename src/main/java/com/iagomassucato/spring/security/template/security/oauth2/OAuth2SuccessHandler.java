package com.iagomassucato.spring.security.template.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionEntity;
import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import com.iagomassucato.spring.security.template.security.auth.AuthResponse;
import com.iagomassucato.spring.security.template.security.jwt.JwtGenerator;
import com.iagomassucato.spring.security.template.security.jwt.JwtToken;
import com.iagomassucato.spring.security.template.security.refreshtoken.RefreshTokenCreator;
import com.iagomassucato.spring.security.template.security.session.SessionCreator;
import com.iagomassucato.spring.security.template.security.session.SessionEntity;
import com.iagomassucato.spring.security.template.security.userdetails.UserDetailsImpl;
import com.iagomassucato.spring.security.template.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2AuthenticationService oAuth2AuthenticationService;
    private final SessionCreator sessionCreator;
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenCreator refreshTokenCreator;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        UserDetailsImpl userDetailsImpl = oAuth2AuthenticationService.authenticate(authentication);
        UserEntity userEntity = userDetailsImpl.getUserEntity();
        SessionEntity sessionEntity = sessionCreator.create(
                userEntity,
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
        );
        Long userId = userEntity.getId();
        Long sessionId = sessionEntity.getId();
        Set<String> roles = userEntity.getRoleEntitySet()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
        Set<String> permissions = userEntity.getRoleEntitySet()
                .stream()
                .flatMap(role -> role.getPermissionEntitySet().stream())
                .map(PermissionEntity::getName)
                .collect(Collectors.toSet());
        JwtToken newAccessToken = jwtGenerator.generateAccessToken(userId, sessionId, roles, permissions);
        JwtToken refreshToken = jwtGenerator.generateRefreshToken(userId, sessionId);
        refreshTokenCreator.create(refreshToken, sessionEntity);
        AuthResponse authResponse = new AuthResponse(newAccessToken.token(), refreshToken.token());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(response.getOutputStream(), authResponse);
    }
}
