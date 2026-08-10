package com.iagomassucato.spring.security.template.security.resetpassword;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/forgot-password")
@RequiredArgsConstructor
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    @PostMapping
    public ResponseEntity<ResetPasswordResponse> forgotPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        ResetPasswordResponse response = resetPasswordService.forgotPassword(resetPasswordRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ConfirmPasswordResetRequest request) {
        resetPasswordService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }
}
