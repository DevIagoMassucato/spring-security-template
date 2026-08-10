package com.iagomassucato.spring.security.template.accesscontrol.role;

import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionEntity;
import com.iagomassucato.spring.security.template.accesscontrol.permission.PermissionFinder;
import com.iagomassucato.spring.security.template.shared.PatchValidator;
import jakarta.transaction.Transactional;
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
    private final PatchValidator patchValidator;

    public RoleResponse create(RoleRequest roleRequest){
        RoleEntity roleEntity = RoleEntity.create(
                roleRequest.getName(),
                findPermissionsByIds(roleRequest.getPermissionIds())
        );
        roleRepository.save(roleEntity);
        return RoleResponse.fromEntity(roleEntity);
    }

    @Transactional
    public RoleResponse update(Long id, RolePatchRequest rolePatchRequest) {
        patchValidator.validate(rolePatchRequest);
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(id);
        if (rolePatchRequest.getName() != null) {
            roleEntity.updateName(rolePatchRequest.getName());
        }
        if (rolePatchRequest.getPermissionIds() != null) {
            roleEntity.updatePermissionEntitySet(findPermissionsByIds(rolePatchRequest.getPermissionIds()));
        }
        roleRepository.save(roleEntity);
        return RoleResponse.fromEntity(roleEntity);
    }

    @Transactional
    public RoleResponse replace(Long id, RoleRequest roleRequest) {
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(id);
        roleEntity.updateName(roleRequest.getName());
        roleEntity.updatePermissionEntitySet(findPermissionsByIds(roleRequest.getPermissionIds()));
        roleRepository.save(roleEntity);
        return RoleResponse.fromEntity(roleEntity);
    }

    @Transactional
    public void addPermission(Long roleId, Long permissionId){
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(roleId);
        PermissionEntity permissionEntity = permissionFinder.findByIdOrThrow(permissionId);
        roleEntity.addPermission(permissionEntity);
    }

    @Transactional
    public void removePermission(Long roleId, Long permissionId) {
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(roleId);
        PermissionEntity permissionEntity = permissionFinder.findByIdOrThrow(permissionId);
        roleEntity.removePermission(permissionEntity);
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

    private Set<PermissionEntity> findPermissionsByIds(Set<Long> permissionIds){
        return permissionFinder.findAllByIdInOrThrow(permissionIds);
    }
}
