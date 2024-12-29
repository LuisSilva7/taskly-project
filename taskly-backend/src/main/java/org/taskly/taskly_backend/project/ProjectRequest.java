package org.taskly.taskly_backend.project;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProjectRequest(
        @NotBlank(message = "Name cannot be null")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String name,
        @NotBlank(message = "Description cannot be null")
        @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
        String description,
        @NotNull(message = "Budget cannot be null")
        @DecimalMin(value = "0.01", message = "Budget must be greater than 0")
        BigDecimal budget,
        @NotNull(message = "Start date cannot be null")
        @FutureOrPresent(message = "Start date must be today or in the future")
        LocalDate startDate,
        @NotNull(message = "Expected end date cannot be null")
        @FutureOrPresent(message = "Expected end date must be in the future")
        LocalDate expectedEndDate,
        List<String> attachmentsUrl,
        @NotBlank(message = "Project status cannot be null")
        String status,
        @NotBlank(message = "Project category cannot be null")
        String category,
        @NotBlank(message = "Project priority cannot be null")
        String priority,
        @NotNull(message = "Project manager cannot be null")
        Long projectManagerId,
        @NotEmpty(message = "Project members cannot be empty")
        List<Long> projectMembersId,
        LocalDate endDate,
        Integer progress,
        List<Long> tasksId
) {
}