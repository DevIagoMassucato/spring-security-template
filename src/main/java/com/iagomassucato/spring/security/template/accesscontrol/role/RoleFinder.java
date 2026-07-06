package com.iagomassucato.spring.security.template.accesscontrol.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleFinder {

    private final RoleRepository roleRepository;

    public RoleEntity findByIdOrThrow(Long id){
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("role not found with id: " + id));
    }
}
