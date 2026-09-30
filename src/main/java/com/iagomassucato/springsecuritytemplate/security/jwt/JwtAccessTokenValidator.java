package com.iagomassucato.springsecuritytemplate.security.jwt;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class JwtAccessTokenValidator implements OAuth2TokenValidator<Jwt> {

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        OAuth2TokenValidatorResult oAuth2TokenValidatorResult;
        oAuth2TokenValidatorResult = validateTokenId(jwt);
        if (oAuth2TokenValidatorResult.hasErrors()) return oAuth2TokenValidatorResult;
        oAuth2TokenValidatorResult = validateUserId(jwt);
        if (oAuth2TokenValidatorResult.hasErrors()) return oAuth2TokenValidatorResult;
        oAuth2TokenValidatorResult = validateSessionId(jwt);
        if (oAuth2TokenValidatorResult.hasErrors()) return oAuth2TokenValidatorResult;
        oAuth2TokenValidatorResult = validateTokenType(jwt);
        if (oAuth2TokenValidatorResult.hasErrors()) return oAuth2TokenValidatorResult;
        oAuth2TokenValidatorResult = validateRoles(jwt);
        if (oAuth2TokenValidatorResult.hasErrors()) return oAuth2TokenValidatorResult;
        oAuth2TokenValidatorResult = validatePermissions(jwt);
        if (oAuth2TokenValidatorResult.hasErrors()) return oAuth2TokenValidatorResult;
        return OAuth2TokenValidatorResult.success();
    }

    private OAuth2TokenValidatorResult validateTokenId(Jwt jwt) {
        OAuth2TokenValidatorResult oAuth2TokenValidatorResult = validateHasClaim(jwt, JwtClaimNames.JTI);
        if (oAuth2TokenValidatorResult.hasErrors()) {
            return oAuth2TokenValidatorResult;
        }
        Object claimValue = jwt.getClaim(JwtClaimNames.JTI);
        oAuth2TokenValidatorResult = validateIsString(claimValue, "invalid token id");
        if (oAuth2TokenValidatorResult.hasErrors()) {
            return oAuth2TokenValidatorResult;
        }
        return validateUUIDFormat((String) claimValue);
    }

    private OAuth2TokenValidatorResult validateUserId(Jwt jwt) {
        return validateStringClaim(jwt, JwtClaimNames.SUB, "invalid user id in token");
    }

    private OAuth2TokenValidatorResult validateSessionId(Jwt jwt) {
        return validateStringClaim(jwt, JwtClaimsConstants.SESSION_ID, "invalid session id in token");
    }

    private OAuth2TokenValidatorResult validateTokenType(Jwt jwt) {
        OAuth2TokenValidatorResult oAuth2TokenValidatorResult = validateHasClaim(jwt, JwtClaimsConstants.TYPE);
        if (oAuth2TokenValidatorResult.hasErrors()) {
            return oAuth2TokenValidatorResult;
        }
        Object claimValue = jwt.getClaim(JwtClaimsConstants.TYPE);
        oAuth2TokenValidatorResult = validateIsString(claimValue, "invalid token type");
        if (oAuth2TokenValidatorResult.hasErrors()) {
            return oAuth2TokenValidatorResult;
        }
        if (!JwtType.ACCESS.name().equals(claimValue)) {
            return createErrorResult("invalid token type");
        }

        return OAuth2TokenValidatorResult.success();
    }

    private OAuth2TokenValidatorResult validateRoles(Jwt jwt) {
        OAuth2TokenValidatorResult oAuth2TokenValidatorResult =
                validateStringListClaim(jwt, JwtClaimsConstants.ROLES, "roles");
        if (oAuth2TokenValidatorResult.hasErrors()) {
            return oAuth2TokenValidatorResult;
        }
        List<?> roles = jwt.getClaim(JwtClaimsConstants.ROLES);
        if (roles.isEmpty()) {
            return createErrorResult("invalid roles in token");
        }
        return OAuth2TokenValidatorResult.success();
    }

    private OAuth2TokenValidatorResult validatePermissions(Jwt jwt) {
        return validateStringListClaim(jwt, JwtClaimsConstants.PERMISSIONS, "permissions");
    }

    private OAuth2TokenValidatorResult validateStringListClaim(Jwt jwt, String claimName, String fieldName) {
        OAuth2TokenValidatorResult oAuth2TokenValidatorResult = validateHasClaim(jwt, claimName);
        if (oAuth2TokenValidatorResult.hasErrors()) {
            return oAuth2TokenValidatorResult;
        }
        Object claimValue = jwt.getClaim(claimName);
        if (!(claimValue instanceof List<?> list) || !list.stream().allMatch(String.class::isInstance)) {
            return createErrorResult("invalid " + fieldName + " in token");
        }
        return OAuth2TokenValidatorResult.success();
    }

    private OAuth2TokenValidatorResult validateStringClaim(Jwt jwt, String claimName, String errorDescription) {
        OAuth2TokenValidatorResult oAuth2TokenValidatorResult = validateHasClaim(jwt, claimName);
        if (oAuth2TokenValidatorResult.hasErrors()) {
            return oAuth2TokenValidatorResult;
        }
        Object claimValue = jwt.getClaim(claimName);
        oAuth2TokenValidatorResult = validateIsString(claimValue, errorDescription);
        if (oAuth2TokenValidatorResult.hasErrors()) {
            return oAuth2TokenValidatorResult;
        }
        return validateStringIdFormat((String) claimValue, errorDescription);
    }

    private OAuth2TokenValidatorResult validateIsString(Object claimValue, String errorDescription) {
        if (!(claimValue instanceof String)) {
            return createErrorResult(errorDescription);
        }
        return OAuth2TokenValidatorResult.success();
    }

    private OAuth2TokenValidatorResult validateStringIdFormat(String value, String errorDescription) {
        if (value.isBlank() || !value.matches("[0-9]+") || value.startsWith("0")) {
            return createErrorResult(errorDescription);
        }
        return OAuth2TokenValidatorResult.success();
    }

    private OAuth2TokenValidatorResult validateUUIDFormat(String tokenId) {
        if (!tokenId.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$")) {
            return createErrorResult("invalid token id");
        }
        return OAuth2TokenValidatorResult.success();
    }

    private OAuth2TokenValidatorResult validateHasClaim(Jwt jwt, String claimName) {
        if (!jwt.hasClaim(claimName)) {
            return createErrorResult("missing token claim");
        }
        return OAuth2TokenValidatorResult.success();
    }

    private OAuth2TokenValidatorResult createErrorResult(String description) {
        return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", description, null));
    }
}
