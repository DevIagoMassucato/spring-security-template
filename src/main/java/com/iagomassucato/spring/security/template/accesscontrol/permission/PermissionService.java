package com.iagomassucato.spring.security.template.accesscontrol.permission;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionFinder permissionFinder;

    public PermissionResponse create(PermissionRequest permissionRequest){
        PermissionEntity permissionEntity = PermissionEntity.create(permissionRequest.name());
        permissionRepository.save(permissionEntity);
        return PermissionResponse.fromEntity(permissionEntity);
    }

    @Transactional
    public PermissionResponse replace(Long id, PermissionRequest permissionRequest){
        PermissionEntity permissionEntity = permissionFinder.findByIdOrThrow(id);
        permissionEntity.updateName(permissionRequest.name());
        return PermissionResponse.fromEntity(permissionEntity);
    }

    public List<PermissionResponse> findAll() {
        return permissionRepository.findAll()
                .stream()
                .map(PermissionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public PermissionResponse findById(Long id){
        PermissionEntity permissionEntity = permissionFinder.findByIdOrThrow(id);
        return PermissionResponse.fromEntity(permissionEntity);
    }

    public void delete(Long id){
        PermissionEntity permissionEntity = permissionFinder.findByIdOrThrow(id);
        permissionRepository.delete(permissionEntity);
    }
}
