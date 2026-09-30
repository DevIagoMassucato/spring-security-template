package com.iagomassucato.springsecuritytemplate.accesscontrol.permission;

import com.iagomassucato.springsecuritytemplate.shared.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "permissions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_permissions_name", columnNames = "name")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PermissionEntity extends AbstractEntity {

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

    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        return name.trim().toUpperCase();
    }
}