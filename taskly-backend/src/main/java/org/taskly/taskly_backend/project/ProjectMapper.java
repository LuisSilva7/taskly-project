package org.taskly.taskly_backend.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.project.enums.ProjectCategory;
import org.taskly.taskly_backend.project.enums.ProjectPriority;
import org.taskly.taskly_backend.project.enums.ProjectStatus;
import org.taskly.taskly_backend.task.Task;
import org.taskly.taskly_backend.user.User;
import org.taskly.taskly_backend.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectMapper {

    private final UserRepository userRepository;

    public Project toProjectEntity(ProjectRequest request) {

        Optional<User> projectManager = userRepository.findById(request.projectManagerId());
        if(projectManager.isEmpty()) {
            throw new ResourceNotFoundException("User with ID: " + request.projectManagerId() + " does not exist!");
        }

        List<User> projectMembers = request.projectMembersId().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + id + " does not exist!"))
                )
                .toList();

        return Project.builder()
                .name(request.name())
                .description(request.description())
                .budget(request.budget())
                .startDate(request.startDate())
                .expectedEndDate(request.expectedEndDate())
                .endDate(null)
                .progress(0)
                .status(ProjectStatus.fromString(request.status()))
                .category(ProjectCategory.fromString(request.category()))
                .priority(ProjectPriority.fromString(request.priority()))
                .projectManager(projectManager.get())
                .projectMembers(projectMembers)
                .tasks(null)
                .build();
    }

    public ProjectResponse toProjectResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getExpectedEndDate(),
                project.getEndDate(),
                project.getProgress(),
                project.getStatus().getStatus(),
                project.getCategory().getCategory(),
                project.getPriority().getPriority(),
                project.getProjectManager().getId(),
                project.getProjectMembers().stream().map(User::getId).toList(),
                project.getTasks().stream().map(Task::getId).toList()
        );
    }
}
