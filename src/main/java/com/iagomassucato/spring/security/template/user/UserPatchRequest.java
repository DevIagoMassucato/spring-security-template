package com.iagomassucato.spring.security.template.user;

import lombok.Getter;

@Getter
public class UserPatchRequest {
    private String username;
    private String password;
    private Long roleId;
}
