package com.iagomassucato.spring.security.template.accesscontrol.role;

import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionEntity;
import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleFinder roleFinder;
    private final PermissionFinder permissionFinder;

    public RoleResponse create(RoleRequest roleRequest){
        RoleEntity roleEntity = toEntity(roleRequest);
        RoleEntity roleEntitySaved = save(roleEntity);
        return RoleResponse.fromEntity(roleEntitySaved);
    }

    public RoleResponse update(Long id, RolePatchRequest rolePatchRequest) {
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(id);
        if (rolePatchRequest.getName() != null) {
            roleEntity.updateName(rolePatchRequest.getName());
        }
        if (rolePatchRequest.getPermissionIds() != null) {
            roleEntity.updatePermissionEntitySet(getPermissions(rolePatchRequest.getPermissionIds()));
        }
        RoleEntity roleEntitySaved = save(roleEntity);
        return RoleResponse.fromEntity(roleEntitySaved);
    }

    public RoleResponse replace(Long id, RoleRequest roleRequest) {
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(id);
        roleEntity.updateName(roleRequest.getName());
        roleEntity.updatePermissionEntitySet(getPermissions(roleRequest.getPermissionIds()));
        RoleEntity roleEntitySaved = save(roleEntity);
        return RoleResponse.fromEntity(roleEntitySaved);
    }

    public void addPermission(Long roleId, Long permissionId){
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(roleId);
        PermissionEntity permissionEntity = permissionFinder.findByIdOrThrow(permissionId);
        roleEntity.getPermissionEntitySet().add(permissionEntity);
        save(roleEntity);
    }

    public void removePermission(Long roleId, Long permissionId){
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(roleId);
        PermissionEntity permissionEntity = permissionFinder.findByIdOrThrow(permissionId);
        roleEntity.getPermissionEntitySet().remove(permissionEntity);
        save(roleEntity);
    }

    public List<RoleResponse> findAll(){
        return roleRepository.findAll()
                .stream()
                .map(RoleResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public RoleResponse findById(Long id) {
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(id);
        return RoleResponse.fromEntity(roleEntity);
    }

    public void delete(Long id){
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(id);
        roleRepository.delete(roleEntity);
    }

    private RoleEntity toEntity(RoleRequest roleRequest){
        return RoleEntity.builder()
                .name(roleRequest.getName())
                .permissionEntitySet(getPermissions(roleRequest.getPermissionIds()))
                .build();
    }

    private RoleEntity save(RoleEntity roleEntity){
        return roleRepository.save(roleEntity);
    }

    private Set<PermissionEntity> getPermissions(Set<Long> permissionIds){
        return permissionFinder.findAllByIdIn(permissionIds);
    }
}
