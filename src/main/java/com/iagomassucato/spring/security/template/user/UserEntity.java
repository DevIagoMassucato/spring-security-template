package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
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

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity roleEntity;

    @Builder
    public UserEntity(String username, String password, RoleEntity roleEntity){
        this.username = validateUsername(username);
        this.password = validatePassword(password);
        this.roleEntity = validateRoleEntity(roleEntity);
    }

    public void updateUsername(String username){
        this.username = validateUsername(username);
    }

    public void updatePassword(String password){
        this.password = validatePassword(password);
    }

    public void updateRoleEntity(RoleEntity roleEntity){
        this.roleEntity = validateRoleEntity(roleEntity);
    }

    private String validateUsername(String username){
        return validateString(username, "username");
    }

    private String validatePassword(String password){
        return validateString(password, "password");
    }

    private RoleEntity validateRoleEntity(RoleEntity roleEntity){
        if (roleEntity == null){
            throw new IllegalArgumentException("role is required");
        }
        return roleEntity;
    }

    private String validateString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value;
    }
}
