package org.taskly.taskly_backend.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProjectCategory{

    MARKETING("Marketing"),
    TECHNOLOGY("Technology"),
    FINANCE("Finance"),
    DESIGN("Design"),
    RESEARCH("Research"),
    DEVELOPMENT("Development"),
    EDUCATION("Education"),
    OPERATIONS("Operations"),
    HR("Human Resources"),
    SALES("Sales");

    private final String category;

    public static ProjectCategory fromString(String category) {
        for (ProjectCategory projectCategory : ProjectCategory.values()) {
            if (projectCategory.getCategory().equalsIgnoreCase(category)) {
                return projectCategory;
            }
        }
        throw new IllegalArgumentException("Unknown project category: " + category);
    }
}
