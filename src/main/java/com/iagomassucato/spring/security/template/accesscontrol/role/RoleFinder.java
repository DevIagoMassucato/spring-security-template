package com.iagomassucato.spring.security.template.accesscontrol.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleFinder {

    private final RoleRepository roleRepository;

    public RoleEntity findByIdOrThrow(Long id){
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("role not found with id: " + id));
    }

    public Set<RoleEntity> findAllByIdsOrThrow(Set<Long> ids) {
        List<RoleEntity> roleEntityList = roleRepository.findAllById(ids);
        Set<Long> foundIds = roleEntityList
                .stream()
                .map(RoleEntity::getId)
                .collect(Collectors.toSet());
        Set<Long> missingIds = new HashSet<>(ids);
        missingIds.removeAll(foundIds);
        if (!missingIds.isEmpty()) {
            throw new NoSuchElementException("role not found with id: " + missingIds);
        }
        return new HashSet<>(roleEntityList);
    }
}
