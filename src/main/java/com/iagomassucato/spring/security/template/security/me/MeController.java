package com.iagomassucato.spring.security.template.security.me;

import com.iagomassucato.spring.security.template.security.session.SessionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class MeController {

    private final MeService meService;

    @PatchMapping
    public ResponseEntity<MeResponse> update(@Valid @RequestBody MePatchRequest mePatchRequest) {
        MeResponse meResponse = meService.update(mePatchRequest);
        return ResponseEntity.ok(meResponse);
    }

    @PutMapping
    public ResponseEntity<MeResponse> replace(@Valid @RequestBody MeRequest meRequest) {
        MeResponse meResponse = meService.replace(meRequest);
        return ResponseEntity.ok(meResponse);
    }

    @GetMapping
    public ResponseEntity<MeResponse> findMe() {
        MeResponse meResponse = meService.findMe();
        return ResponseEntity.ok(meResponse);
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<SessionResponse>> findActiveSessions() {
        List<SessionResponse> sessions = meService.findActiveSessions();
        return ResponseEntity.ok(sessions);
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<Void> revokeSession(@PathVariable Long sessionId) {
        meService.revokeSession(sessionId);
        return ResponseEntity.noContent().build();
    }
}
