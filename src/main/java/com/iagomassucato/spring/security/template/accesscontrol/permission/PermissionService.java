package com.iagomassucato.spring.security.template.accesscontrol.permission;

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
        PermissionEntity permissionEntity = toEntity(permissionRequest);
        PermissionEntity permissionEntitySaved = save(permissionEntity);
        return PermissionResponse.fromEntity(permissionEntitySaved);
    }

    public PermissionResponse replace(Long id, PermissionRequest permissionRequest){
        PermissionEntity permissionEntity = permissionFinder.findByIdOrThrow(id);
        permissionEntity.updateName(permissionRequest.getName());
        PermissionEntity permissionEntitySaved = save(permissionEntity);
        return PermissionResponse.fromEntity(permissionEntitySaved);
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

    private PermissionEntity toEntity(PermissionRequest permissionRequest){
        return PermissionEntity.builder()
                .name(permissionRequest.getName())
                .build();
    }

    private PermissionEntity save(PermissionEntity permissionEntity){
        return permissionRepository.save(permissionEntity);
    }
}
