package com.iagomassucato.spring.security.template.accesscontrol.role;

import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_roles_name", columnNames = "name")
        }
)
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
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
            joinColumns = @JoinColumn(name = "role_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "permission_id", nullable = false)
    )
    private Set<PermissionEntity> permissionEntitySet = new HashSet<>();

    public static RoleEntity create(String name, Set<PermissionEntity> permissionEntitySet){
        return new RoleEntity(name, permissionEntitySet);
    }

    public void addPermission(PermissionEntity permissionEntity) {
        this.permissionEntitySet.add(permissionEntity);
    }

    public void removePermission(PermissionEntity permissionEntity) {
        this.permissionEntitySet.remove(permissionEntity);
    }

    public void updateName(String name){
        this.name = validateName(name);
    }

    public void updatePermissionEntitySet(Set<PermissionEntity> permissionEntitySet) {
        this.permissionEntitySet = validatePermissionEntitySet(permissionEntitySet);
    }

    private RoleEntity(String name, Set<PermissionEntity> permissionEntitySet){
        this.name = validateName(name);
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
