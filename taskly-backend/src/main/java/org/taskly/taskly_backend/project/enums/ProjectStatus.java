package org.taskly.taskly_backend.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProjectStatus{
    IN_PROGRESS("in_progress"),
    COMPLETED("completed");

    private final String status;

    public static ProjectStatus fromString(String status) {
        for (ProjectStatus projectStatus : ProjectStatus.values()) {
            if (projectStatus.getStatus().equalsIgnoreCase(status)) {
                return projectStatus;
            }
        }
        throw new IllegalArgumentException("Unknown project status: " + status);
    }
}
