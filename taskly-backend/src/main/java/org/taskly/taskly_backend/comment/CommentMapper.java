package org.taskly.taskly_backend.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.task.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentMapper {

    private final TaskRepository taskRepository;

    public Comment toCommentEntity(CommentRequest request, Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if(task.isEmpty()) {
            throw new ResourceNotFoundException("Task with ID: " + taskId + " does not exist!");
        }

        return Comment.builder()
                .text(request.text())
                .task(task.get())
                .build();
    }
}
