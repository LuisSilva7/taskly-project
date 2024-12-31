package org.taskly.taskly_backend.comment;

public record CommentResponse(
        Long id,
        String text,
        Long taskId,
        String fullName
) {
}
