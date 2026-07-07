package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roleEntitySet;

    @Builder
    public UserEntity(String username, String password, Set<RoleEntity> roleEntitySet){
        this.username = validateUsername(username);
        this.password = validatePassword(password);
        this.roleEntitySet = validateRoleEntitySet(roleEntitySet);
    }


    public void updateUsername(String username){
        this.username = validateUsername(username);
    }

    public void updatePassword(String password){
        this.password = validatePassword(password);
    }

    public void updateRoleEntitySet(Set<RoleEntity> roleEntitySet) {
        this.roleEntitySet = validateRoleEntitySet(roleEntitySet);
    }

    private String validateString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value;
    }

    private Set<RoleEntity> validateRoleEntitySet(Set<RoleEntity> roleEntitySet) {

        if (roleEntitySet == null || roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("role is required");
        }

        return roleEntitySet;
    }

    private String validateUsername(String username){
        return validateString(username, "username");
    }

    private String validatePassword(String password){
        return validateString(password, "password");
    }
}
