package com.iagomassucato.spring.security.template.accesscontrol.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionFinder {

    private final PermissionRepository permissionRepository;

    public PermissionEntity findByIdOrThrow(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("permission not found with id: " + id));
    }

    public Set<PermissionEntity> findAllByIdIn(Set<Long> permissionIds) {
        Set<PermissionEntity> permissionEntitySet = permissionRepository.findAllByIdIn(permissionIds);
        if (permissionEntitySet.size() != permissionIds.size()) {
            Set<Long> foundIds = permissionEntitySet.stream()
                    .map(PermissionEntity::getId)
                    .collect(Collectors.toSet());
            Set<Long> missingIds = new HashSet<>(permissionIds);
            missingIds.removeAll(foundIds);
            throw new NoSuchElementException("permission not found with id: " + missingIds);
        }
        return permissionEntitySet;
    }
}
