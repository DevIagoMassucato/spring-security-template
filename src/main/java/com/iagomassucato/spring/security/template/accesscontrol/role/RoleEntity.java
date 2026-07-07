package com.iagomassucato.spring.security.template.accesscontrol.role;

import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_role_name", columnNames = "name")
        }
)
@NoArgsConstructor
@Getter
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissionEntitySet = new HashSet<>();

    @Builder
    public RoleEntity(String name, Set<PermissionEntity> permissionEntitySet){
        this.name = validateName(name);
        this.permissionEntitySet = validatePermissionEntitySet(permissionEntitySet);

    }

    public void updateName(String name){
        this.name = validateName(name);
    }

    public void updatePermissionEntitySet(Set<PermissionEntity> permissionEntitySet) {
        this.permissionEntitySet = validatePermissionEntitySet(permissionEntitySet);
    }

    private String validateName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        return value.trim().toUpperCase();
    }

    private Set<PermissionEntity> validatePermissionEntitySet(Set<PermissionEntity> value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("permissionEntitySet is required");
        }
        return value;
    }
}
