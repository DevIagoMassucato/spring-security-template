package com.iagomassucato.spring.security.template.security.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtClaimsConstants {
    public static final String TYPE = "type";
    public static final String SESSION_ID = "sid";
    public static final String ROLES = "roles";
    public static final String PERMISSIONS = "permissions";
}
