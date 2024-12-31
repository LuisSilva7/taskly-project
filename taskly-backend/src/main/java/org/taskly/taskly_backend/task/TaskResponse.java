package org.taskly.taskly_backend.task;

import org.taskly.taskly_backend.comment.CommentResponse;

import java.time.LocalDate;
import java.util.List;

public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDate deadline,
        String status,
        Long projectId,
        List<Long> taskMembersId,
        List<CommentResponse> taskComments
) {
}
