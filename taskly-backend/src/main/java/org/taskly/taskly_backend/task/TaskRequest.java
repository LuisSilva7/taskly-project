package org.taskly.taskly_backend.task;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record TaskRequest(
        @NotBlank(message = "Name cannot be null")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String title,
        @NotBlank(message = "Description cannot be null")
        @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
        String description,
        @NotNull(message = "Deadline cannot be null")
        @FutureOrPresent(message = "Deadline must be today or in the future")
        LocalDate deadline,
        @NotBlank(message = "Project status cannot be null")
        String status,
        @NotEmpty(message = "Task members cannot be empty")
        List<Long> taskMembersId
) {
}
