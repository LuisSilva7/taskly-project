package org.taskly.taskly_backend.auth;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RegistrationRequest(
        @NotEmpty(message = "Firstname is mandatory")
        @NotBlank(message = "Firstname is mandatory")
        String firstname,
        @NotEmpty(message = "Lastname is mandatory")
        @NotBlank(message = "Lastname is mandatory")
        String lastname,
        @Past(message = "Date of birth must be in the past.")
        LocalDate dateOfBirth,
        @NotNull(message = "Job role cannot be null.")
        @Size(min = 2, max = 40, message = "Job role must be between 2 and 50 characters.")
        String jobRole,
        String photoUrl,
        @NotEmpty(message = "Email is mandatory")
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email is not valid")
        String email,
        @NotEmpty(message = "Password is mandatory")
        @NotBlank(message = "Password is mandatory")
        @Size(min = 5, message = "Password should be 5 characters long minimum")
        String password
) {
}
