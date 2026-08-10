package com.iagomassucato.spring.security.template.accesscontrol.permission;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "permissions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_permissions_name", columnNames = "name")
        }
)
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public static PermissionEntity create(String name){
        return new PermissionEntity(name);
    }

    public void updateName(String name){
        this.name = validateName(name);
    }

    private PermissionEntity(String name){
        this.name = validateName(name);
    }

    private String validateName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        return value.trim().toUpperCase();
    }
}