package org.taskly.taskly_backend.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProjectPriority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String priority;

    public static ProjectPriority fromString(String priority) {
        for (ProjectPriority projectPriority : ProjectPriority.values()) {
            if (projectPriority.getPriority().equalsIgnoreCase(priority)) {
                return projectPriority;
            }
        }
        throw new IllegalArgumentException("Unknown project priority: " + priority);
    }
}
