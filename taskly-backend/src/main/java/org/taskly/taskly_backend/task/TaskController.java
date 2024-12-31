package org.taskly.taskly_backend.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taskly.taskly_backend.common.ApiResponse;
import org.taskly.taskly_backend.common.PageResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    @PostMapping("{project-id}/tasks")
    public ResponseEntity<ApiResponse<?>> createTask(@RequestBody @Valid TaskRequest request,
                                                     @PathVariable("project-id") Long projectId) {
        Long taskId = taskService.createTaskByProjectId(request, projectId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Task with ID: " + taskId + " created successfully!", null
        ));
    }

    @GetMapping("{project-id}/tasks")
    public ResponseEntity<ApiResponse<PageResponse<TaskResponse>>> findAllTasks(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int size,
            @PathVariable("project-id") Long projectId
    ) {
        PageResponse<TaskResponse> response = taskService.findAllTasksByProjectId(page, size, projectId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Tasks obtained successfully!", response));
    }
}
