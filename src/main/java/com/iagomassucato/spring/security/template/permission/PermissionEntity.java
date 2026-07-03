package com.iagomassucato.spring.security.template.permission;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(
        name = "permissions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_permission_name", columnNames = "name")
        }
)
@NoArgsConstructor
@Getter
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    public PermissionEntity(String name){
        this.name = validateName(name);
    }

    public void updateName(String name){
        this.name = validateName(name);
    }

    private String validateName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        return value.trim().toUpperCase();
    }
}