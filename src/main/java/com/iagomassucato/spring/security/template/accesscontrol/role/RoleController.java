package com.iagomassucato.spring.security.template.accesscontrol.role;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasRole('TI')")
    @PostMapping
    public ResponseEntity<RoleResponse> create(@Valid @RequestBody RoleRequest roleRequest){
        RoleResponse roleResponse = roleService.create(roleRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @PatchMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable Long id, @RequestBody RolePatchRequest rolePatchRequest) {
        RoleResponse roleResponse = roleService.update(id, rolePatchRequest);
        return ResponseEntity.ok(roleResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> replace(@PathVariable Long id, @Valid @RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.replace(id, roleRequest);
        return ResponseEntity.ok(roleResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @PostMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<Void> addPermission(@PathVariable Long roleId, @PathVariable Long permissionId){
        roleService.addPermission(roleId, permissionId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PreAuthorize("hasRole('TI')")
    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    public ResponseEntity<Void> removePermission(@PathVariable Long roleId, @PathVariable Long permissionId){
        roleService.removePermission(roleId, permissionId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PreAuthorize("hasRole('TI')")
    @GetMapping
    public ResponseEntity<List<RoleResponse>> findAll(){
        List<RoleResponse> roleResponseList = roleService.findAll();
        return ResponseEntity.ok(roleResponseList);
    }

    @PreAuthorize("hasRole('TI')")
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findById(@PathVariable Long id){
        RoleResponse roleResponse = roleService.findById(id);
        return ResponseEntity.ok(roleResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
