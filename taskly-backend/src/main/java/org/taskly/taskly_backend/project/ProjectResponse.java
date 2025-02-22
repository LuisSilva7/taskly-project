package org.taskly.taskly_backend.project;

import java.time.LocalDate;
import java.util.List;

public record ProjectResponse (
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate expectedEndDate,
        LocalDate endDate,
        double progress,
        String status,
        String category,
        String priority,
        Long projectManagerId,
        List<Long> projectMembersId,
        List<Long> projectTasksId
) {
}
