package com.iagomassucato.spring.security.template.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionFinder {

    private final PermissionRepository permissionRepository;

    public PermissionEntity findByIdOrThrow(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("permission not found with id: " + id));
    }

    public Set<PermissionEntity> findAllByIdIn(Set<Long> permissionIds){
        Set<PermissionEntity> permissionEntitySet = permissionRepository.findAllByIdIn(permissionIds);
        if(permissionEntitySet.size() != permissionIds.size()){
            throw new NoSuchElementException("permission not found with ids: " + permissionIds);
        }
        return permissionEntitySet;
    }
}
