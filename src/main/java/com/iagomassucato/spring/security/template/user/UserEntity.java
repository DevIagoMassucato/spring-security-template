package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_users_email", columnNames = "email")
        })
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roleEntitySet;

    public static UserEntity create(String username, String email, Set<RoleEntity> roleEntitySet){
        return new UserEntity(username, email, roleEntitySet);
    }

    public void updateUsername(String username) {
        this.username = validateUsername(username);
    }

    public void updateEmail(String email) {
        this.email = validateEmail(email);
    }

    public void updateRoleEntitySet(Set<RoleEntity> roleEntitySet) {
        this.roleEntitySet = validateRoleEntitySet(roleEntitySet);
    }

    private UserEntity(String username, String email, Set<RoleEntity> roleEntitySet) {
        this.username = validateUsername(username);
        this.email = validateEmail(email);
        this.roleEntitySet = validateRoleEntitySet(roleEntitySet);
    }

    private String validateUsername(String username) {
        username = validateString(username, "username");
        return username;
    }

    private String validateEmail(String email) {
        email = validateString(email, "email");
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("email is invalid");
        }
        return email;
    }

    private String validateString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value.toLowerCase();
    }

    private Set<RoleEntity> validateRoleEntitySet(Set<RoleEntity> roleEntitySet) {
        if (roleEntitySet == null || roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("role is required");
        }
        return roleEntitySet;
    }
}
