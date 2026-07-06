package com.iagomassucato.spring.security.template.accesscontrol.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PreAuthorize("hasRole('TI')")
    @PostMapping
    public ResponseEntity<PermissionResponse> create(@Valid @RequestBody PermissionRequest permissionRequest){
        PermissionResponse permissionResponse = permissionService.create(permissionRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(permissionResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponse> replace(
            @PathVariable Long id, @Valid @RequestBody PermissionRequest permissionRequest){
        PermissionResponse permissionResponse = permissionService.replace(id, permissionRequest);
        return ResponseEntity.ok(permissionResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @GetMapping
    public ResponseEntity<List<PermissionResponse>> findAll(){
        List<PermissionResponse> permissionResponseList = permissionService.findAll();
        return ResponseEntity.ok(permissionResponseList);
    }

    @PreAuthorize("hasRole('TI')")
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> findById(@PathVariable Long id){
        PermissionResponse permissionResponse = permissionService.findById(id);
        return ResponseEntity.ok(permissionResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
