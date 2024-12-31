package org.taskly.taskly_backend.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.taskly.taskly_backend.comment.Comment;
import org.taskly.taskly_backend.comment.CommentRepository;
import org.taskly.taskly_backend.comment.CommentResponse;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.project.Project;
import org.taskly.taskly_backend.project.ProjectRepository;
import org.taskly.taskly_backend.user.User;
import org.taskly.taskly_backend.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskMapper {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public Task toTaskEntity(TaskRequest request, Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()) {
            throw new ResourceNotFoundException("Project with ID: " + projectId + " does not exist!");
        }

        List<User> taskMembers = request.taskMembersId().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + id + " does not exist!"))
                )
                .toList();

        return Task.builder()
                .title(request.title())
                .description(request.description())
                .status(TaskStatus.fromString(request.status()))
                .deadline(request.deadline())
                .project(project.get())
                .taskMembers(taskMembers)
                .taskComments(null)
                .build();
    }

    public TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDeadline(),
                task.getStatus().getStatus(),
                task.getProject().getId(),
                task.getTaskMembers().stream().map(User::getId).toList(),
                convertCommentsToCommentResponse(task.getTaskComments())
        );
    }

    public List<CommentResponse> convertCommentsToCommentResponse(List<Comment> taskComments) {
        return taskComments.stream()
                .map(comment -> {
                    User user = userRepository.findByEmail(comment.getCreatedBy()).orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "User with ID: " + comment.getCreatedBy() + " does not exist!"));

                    return new CommentResponse(comment.getId(), comment.getText(),
                            comment.getTask().getId(), user.fullname());
                })
                .toList();
    }
}
