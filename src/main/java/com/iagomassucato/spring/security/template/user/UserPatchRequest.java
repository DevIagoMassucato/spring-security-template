package com.iagomassucato.spring.security.template.user;

import lombok.Getter;
import java.util.Set;

@Getter
public class UserPatchRequest {
    private String username;
    private String password;
    private String email;
    private Set<Long> roleIds;
}
