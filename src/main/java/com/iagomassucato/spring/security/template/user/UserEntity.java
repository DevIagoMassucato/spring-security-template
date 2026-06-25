package com.iagomassucato.spring.security.template.user;

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
    private String role;

    @Builder
    public UserEntity(String username, String password,String role){
        this.username = validateUsername(username);
        this.password = validatePassword(password);
        this.role = validateRole(role);
    }

    public void updateUsername(String username){
        this.username = validateUsername(username);
    }

    public void updatePassword(String password){
        this.password = validatePassword(password);
    }

    public void updateRole(String role){
        this.role = validateRole(role);
    }

    private String validateUsername(String username){
        username = validateString(username, "username");
        return username.toLowerCase();
    }

    private String validatePassword(String password){
        return validateString(password, "password");
    }

    private String validateRole(String role){
        return validateString(role, "role");
    }

    private String validateString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value;
    }
}
