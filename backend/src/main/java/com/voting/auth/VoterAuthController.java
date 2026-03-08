package com.voting.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@Validated
public class VoterAuthController {

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        String token = UUID.nameUUIDFromBytes((request.voterId() + ":" + Instant.now()).getBytes()).toString();
        return ResponseEntity.ok(new AuthResponse(request.voterId(), token, "AUTHENTICATED"));
    }

    public record AuthRequest(@NotBlank String voterId, @NotBlank String nationalId, @NotBlank String otp) {
    }

    public record AuthResponse(String voterId, String accessToken, String status) {
    }
}
