package org.taskly.taskly_backend.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthenticationRequest(
        @NotEmpty(message = "Email is mandatory")
        @NotNull(message = "Email is mandatory")
        @Email(message = "Email is not valid")
        String email,
        @NotEmpty(message = "Password is mandatory")
        @NotNull(message = "Password is mandatory")
        @Size(min = 5, message = "Password should be 5 characters long minimum")
        String password
) {
}
