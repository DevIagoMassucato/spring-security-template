package com.iagomassucato.spring.security.template.accesscontrol.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    Set<PermissionEntity> findAllByIdIn(Set<Long> ids);
}
