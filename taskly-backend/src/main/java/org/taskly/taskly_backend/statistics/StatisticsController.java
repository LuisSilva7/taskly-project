package org.taskly.taskly_backend.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.taskly.taskly_backend.common.ApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/statistics")
@CrossOrigin
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/completedProjects")
    public ResponseEntity<ApiResponse<CompletedProjectsResponse>> findCompletedProjects(Authentication connectedUser) {
        CompletedProjectsResponse response = statisticsService.findCompletedProjects(connectedUser);

        return ResponseEntity.ok(new ApiResponse<>(
                "Completed projects obtained successfully!", response
        ));
    }

    @GetMapping("/completedTasks")
    public ResponseEntity<ApiResponse<CompletedTasksResponse>> findCompletedTasks(Authentication connectedUser) {
        CompletedTasksResponse response = statisticsService.findCompletedTasks(connectedUser);

        return ResponseEntity.ok(new ApiResponse<>(
                "Completed tasks obtained successfully!", response
        ));
    }
}
