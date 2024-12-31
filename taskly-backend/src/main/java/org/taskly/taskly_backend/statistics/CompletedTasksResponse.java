package org.taskly.taskly_backend.statistics;

public record CompletedTasksResponse(
        Integer completedTasks,
        Integer totalTasks
) {
}
