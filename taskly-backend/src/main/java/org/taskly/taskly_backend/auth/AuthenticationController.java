package org.taskly.taskly_backend.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.taskly.taskly_backend.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(
            @RequestBody @Valid RegistrationRequest request
    ) {
        authenticationService.register(request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Account registered successfully!", null));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        AuthenticationResponse response = authenticationService.authenticate(request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Account authenticated successfully!", response));
    }
}
