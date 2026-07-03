package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.accesscontrol.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_username", columnNames = "username")
        }
)
@NoArgsConstructor
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @Builder
    public UserEntity(String username, String password,RoleEnum roleEnum){
        this.username = validateUsername(username);
        this.password = validatePassword(password);
        this.roleEnum = validateRoleEnum(roleEnum);
    }

    public void updateUsername(String username){
        this.username = validateUsername(username);
    }

    public void updatePassword(String password){
        this.password = validatePassword(password);
    }

    public void updateRoleEnum(RoleEnum roleEnum){
        this.roleEnum = validateRoleEnum(roleEnum);
    }

    private String validateUsername(String username){
        username = validateString(username, "username");
        return username.toLowerCase();
    }

    private String validatePassword(String password){
        return validateString(password, "password");
    }

    private RoleEnum validateRoleEnum(RoleEnum roleEnum){
        if (roleEnum == null){
            throw new IllegalArgumentException("roleEnum is required");
        }
        return roleEnum;
    }

    private String validateString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value;
    }
}
