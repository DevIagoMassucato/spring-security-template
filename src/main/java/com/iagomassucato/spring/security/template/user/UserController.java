package com.iagomassucato.spring.security.template.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('TI')")
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.create(userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserPatchRequest userPatchRequest) {
        UserResponse userResponse = userService.update(id, userPatchRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> replace(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.replace(id, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> userResponseList = userService.findAll();
        return ResponseEntity.ok(userResponseList);
    }

    @PreAuthorize("hasRole('TI')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        UserResponse userResponse = userService.findById(id);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole('TI')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
