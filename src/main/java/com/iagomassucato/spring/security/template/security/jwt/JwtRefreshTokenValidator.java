package com.iagomassucato.spring.security.template.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JwtRefreshTokenValidator {

    private final JwtProperties jwtProperties;

    public void validate(Claims claims) {
        validateTokenId(claims);
        validateUserId(claims);
        validateSessionId(claims);
        validateTokenType(claims);
        validateIssuer(claims);
        validateAudience(claims);
    }

    private void validateTokenId(Claims claims) {
        validateHasClaim(claims, JwtClaimNames.JTI);
        Object claimValue = claims.get(JwtClaimNames.JTI);
        String tokenId = validateIsString(claimValue, "invalid token id");
        validateUUIDFormat(tokenId);

    }

    private void validateIssuer(Claims claims) {
        validateHasClaim(claims, JwtClaimNames.ISS);
        Object claimValue = claims.get(JwtClaimNames.ISS);
        String issuer = validateIsString(claimValue, "invalid token issuer");
        if (!jwtProperties.getIssuer().equals(issuer)) {
            throw new IllegalArgumentException("invalid token issuer");
        }
    }

    private void validateAudience(Claims claims) {
        validateHasClaim(claims, JwtClaimNames.AUD);
        Object claimValue = claims.get(JwtClaimNames.AUD);
        if (!(claimValue instanceof Collection<?> collection)
                || !collection.stream().allMatch(String.class::isInstance)
                || !collection.contains(jwtProperties.getAudience())) {
            throw new IllegalArgumentException("invalid token audience");
        }
    }

    private void validateUserId(Claims claims) {
        validateHasClaim(claims, JwtClaimNames.SUB);
        Object claimValue = claims.get(JwtClaimNames.SUB);
        String userId = validateIsString(claimValue, "invalid user id in token");
        validateStringIdFormat(userId, "user id");
    }

    private void validateSessionId(Claims claims) {
        validateHasClaim(claims, JwtClaimsConstants.SESSION_ID);
        Object claimValue = claims.get(JwtClaimsConstants.SESSION_ID);
        String sessionId = validateIsString(claimValue, "invalid session id in token");
        validateStringIdFormat(sessionId, "session id");
    }

    private void validateTokenType(Claims claims) {
        validateHasClaim(claims, JwtClaimsConstants.TYPE);
        Object claimValue = claims.get(JwtClaimsConstants.TYPE);
        String type = validateIsString(claimValue, "invalid token type");
        if (!JwtType.REFRESH.name().equals(type)) {
            throw new IllegalArgumentException("invalid token type");
        }
    }

    private String validateIsString(Object claimValue, String errorDescription) {
        if (!(claimValue instanceof String stringValue) || stringValue.isBlank()) {
            throw new IllegalArgumentException(errorDescription);
        }
        return stringValue;
    }

    private void validateStringIdFormat(String value, String fieldName) {
        if (!value.matches("[0-9]+") || value.startsWith("0")) {
            throw new IllegalArgumentException("invalid " + fieldName + " in token");
        }
    }

    private void validateUUIDFormat(String tokenId) {
        if (!tokenId.matches(
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$"
        )) {
            throw new IllegalArgumentException("invalid token id");
        }
    }

    private void validateHasClaim(Claims claims, String claimName) {
        if (!claims.containsKey(claimName)) {
            throw new IllegalArgumentException("missing token claim");
        }
    }
}
