package org.taskly.taskly_backend.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.taskly.taskly_backend.project.ProjectRepository;
import org.taskly.taskly_backend.task.TaskRepository;
import org.taskly.taskly_backend.user.User;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public CompletedProjectsResponse findCompletedProjects(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());

        Integer completedProjects = projectRepository.countCompletedProjectsByUserId(user);

        Integer totalProjects = projectRepository.countProjectsByUserId(user);

        return totalProjects == null ? new CompletedProjectsResponse(
                0,
                0
        ) : new CompletedProjectsResponse(
                completedProjects,
                totalProjects
        );
    }

    public CompletedTasksResponse findCompletedTasks(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());

        Integer completedTasks = taskRepository.countCompletedTasksByUserId(user);

        Integer totalTasks = taskRepository.countTasksByUserId(user);

        return totalTasks == null ? new CompletedTasksResponse(
                0,
                0
        ) : new CompletedTasksResponse(
                completedTasks,
                totalTasks
        );
    }
}
