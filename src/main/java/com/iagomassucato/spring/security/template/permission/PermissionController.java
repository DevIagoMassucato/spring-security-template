package com.iagomassucato.spring.security.template.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<PermissionResponse> create(@Valid @RequestBody PermissionRequest permissionRequest){
        PermissionResponse permissionResponse = permissionService.create(permissionRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(permissionResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponse> replace(
            @PathVariable Long id, @Valid @RequestBody PermissionRequest permissionRequest){
        PermissionResponse permissionResponse = permissionService.replace(id, permissionRequest);
        return ResponseEntity.ok(permissionResponse);
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> findAll(){
        List<PermissionResponse> permissionResponseList = permissionService.findAll();
        return ResponseEntity.ok(permissionResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> findById(@PathVariable Long id){
        PermissionResponse permissionResponse = permissionService.findById(id);
        return ResponseEntity.ok(permissionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
