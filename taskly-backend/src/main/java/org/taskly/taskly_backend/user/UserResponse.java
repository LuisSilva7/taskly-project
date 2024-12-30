package org.taskly.taskly_backend.user;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String firstname,
        String lastname,
        LocalDate dateOfBirth,
        String jobRole
) {
}
