package org.taskly.taskly_backend.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taskly.taskly_backend.common.ApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{task-id}/comments")
    public ResponseEntity<ApiResponse<?>> createComment(@RequestBody @Valid CommentRequest request,
                                                        @PathVariable("task-id") Long taskId) {
        Long commentId = commentService.createCommentByTaskId(request, taskId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Comment with ID: " + commentId + " created successfully!", null
        ));
    }
}
