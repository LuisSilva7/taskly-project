package org.taskly.taskly_backend.statistics;

public record CompletedProjectsResponse(
        Integer completedProjects,
        Integer totalProjects
) {
}
