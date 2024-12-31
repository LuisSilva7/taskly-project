package org.taskly.taskly_backend.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.taskly.taskly_backend.common.ApiResponse;
import org.taskly.taskly_backend.common.PageResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/projects/{project-id}/tasks")
    public ResponseEntity<ApiResponse<?>> createTask(@RequestBody @Valid TaskRequest request,
                                                     @PathVariable("project-id") Long projectId) {
        Long taskId = taskService.createTaskByProjectId(request, projectId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Task with ID: " + taskId + " created successfully!", null
        ));
    }

    @GetMapping("/projects/{project-id}/tasks")
    public ResponseEntity<ApiResponse<PageResponse<TaskResponse>>> findAllTasks(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int size,
            @PathVariable("project-id") Long projectId
    ) {
        PageResponse<TaskResponse> response = taskService.findAllTasksByProjectId(page, size, projectId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Tasks obtained successfully!", response));
    }

    @GetMapping("/tasks/user")
    public ResponseEntity<ApiResponse<PageResponse<TaskResponse>>> findAllTasksByUser(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        PageResponse<TaskResponse> response = taskService.findAllTasksByUser(page, size, connectedUser);

        return ResponseEntity.ok(new ApiResponse<>(
                "Tasks obtained successfully!", response));
    }

    @PutMapping("/tasks/{task-id}/complete")
    public ResponseEntity<ApiResponse<?>> completeTask(
            @PathVariable("task-id") Long taskId
    ) {
        taskService.completeTask(taskId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Task with ID: " + taskId + " completed successfully!", null));
    }
}
