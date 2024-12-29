package org.taskly.taskly_backend.project;

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
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createProject(@RequestBody @Valid ProjectRequest request) {
        Long projectId = projectService.createProject(request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Project with ID: " + projectId + " created successfully!", null
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProjectResponse>>> findAllProjects(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<ProjectResponse> response = projectService.findAllProjects(page, size);

        return ResponseEntity.ok(new ApiResponse<>(
                "Projects obtained successfully!", response));
    }

    @GetMapping("/{project-id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> findProjectById(@PathVariable("project-id") Long projectId) {
        ProjectResponse response = projectService.findProjectById(projectId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Project with ID: " + projectId + " obtained successfully!", response));
    }

    @PutMapping("/{project-id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable("project-id") Long projectId,
            @RequestBody ProjectRequest request
    ) {
        ProjectResponse response = projectService.updateProject(projectId, request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Project with ID: " + projectId + " updated successfully!", response));
    }

    @DeleteMapping("/{project-id}")
    public ResponseEntity<ApiResponse<?>> deleteProject(
            @PathVariable("project-id") Long projectId
    ) {
        projectService.deleteProject(projectId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Project with ID: " + projectId + " deleted successfully!", null));
    }
}