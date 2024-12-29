package org.taskly.taskly_backend.auth;

public record AuthenticationResponse(
        String token,
        String firstname,
        String photoUrl
) {
}
