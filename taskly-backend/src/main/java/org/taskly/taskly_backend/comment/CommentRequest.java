package org.taskly.taskly_backend.comment;

import jakarta.validation.constraints.*;

public record CommentRequest(
        @NotBlank(message = "Name cannot be null")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String text
) {
}
