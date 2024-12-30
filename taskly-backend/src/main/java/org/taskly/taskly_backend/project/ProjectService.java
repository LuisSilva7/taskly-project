package org.taskly.taskly_backend.project;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.taskly.taskly_backend.common.PageResponse;
import org.taskly.taskly_backend.exception.custom.ResourceAlreadyExistsException;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.project.enums.ProjectCategory;
import org.taskly.taskly_backend.project.enums.ProjectPriority;
import org.taskly.taskly_backend.project.enums.ProjectStatus;
import org.taskly.taskly_backend.task.TaskRepository;
import org.taskly.taskly_backend.task.Task;
import org.taskly.taskly_backend.user.User;
import org.taskly.taskly_backend.user.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public Long createProject(ProjectRequest request) {
        if(projectRepository.findByName(request.name()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Project with name: " + request.name() + " already exists!");
        }

        return projectRepository.save(
                projectMapper.toProjectEntity(request)
        ).getId();
    }

    public PageResponse<ProjectResponse> findAllProjects(int pageNumber, int pageSize) {
        pageNumber = Math.max(0, pageNumber);
        pageSize = pageSize > 0 ? pageSize : 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());

        Page<Project> projects = projectRepository.findAllProjects(pageable);

        List<ProjectResponse> response = projects.stream()
                .map(projectMapper::toProjectResponse)
                .toList();

        return new PageResponse<>(
                response,
                projects.getNumber(),
                projects.getSize(),
                projects.getTotalElements(),
                projects.getTotalPages(),
                projects.isFirst(),
                projects.isLast()
        );
    }

    public ProjectResponse findProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project with ID: " + projectId + " does not exist!"));

        return projectMapper.toProjectResponse(project);
    }

    public ProjectResponse updateProject(Long projectId, ProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project with ID: " + projectId + " does not exist!"));

        if(projectRepository.findByName(request.name()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Project with name: " + request.name() + " already exists!");
        }

        if(request.name() != null) {
            if (request.name().length() > 100) {
                throw new IllegalArgumentException("Name must be between 1 and 100 characters!");
            }
            project.setName(request.name());
        }

        if(request.description() != null) {
            if (request.description().length() > 500) {
                throw new IllegalArgumentException("Description must be between 1 and 500 characters!");
            }
            project.setDescription(request.description());
        }


        if(request.budget() != null) {
            if (request.budget().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Budget must be greater than 0!");
            }
            project.setBudget(request.budget());
        }

        if(request.startDate() != null) {
            if (request.startDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Start date must be today or in the future!");
            }
            project.setStartDate(request.startDate());
        }

        if(request.startDate() != null) {
            if (request.expectedEndDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Expected end date must be in the future!");
            }
            project.setExpectedEndDate(request.expectedEndDate());
        }

        if(request.status() != null) {
            project.setStatus(ProjectStatus.fromString(request.status()));
        }

        if(request.category() != null) {
            project.setCategory(ProjectCategory.fromString(request.category()));
        }

        if(request.priority() != null) {
            project.setPriority(ProjectPriority.fromString(request.priority()));
        }

        if(request.projectManagerId() != null) {
            Optional<User> projectManager = userRepository.findById(request.projectManagerId());
            if (projectManager.isEmpty()) {
                throw new ResourceNotFoundException(
                        "User with ID: " + request.projectMembersId() + " does not exist!");
            }
            project.setProjectManager(projectManager.get());
        }

        if (request.projectMembersId() != null) {

            List<User> projectMembers = request.projectMembersId().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "User with ID: " + id + " does not exist!"))
                    )
                    .toList();

            project.setProjectMembers(projectMembers);
        }

        if(request.endDate() != null) {
            if (request.endDate().isBefore(request.startDate())) {
                throw new IllegalArgumentException("End date cannot be before start date!");
            }
            project.setEndDate(request.endDate());
        }

        if(request.progress() != null) {
            if (request.progress() < 0 || request.progress() > 100) {
                throw new IllegalArgumentException("Progress must be between 0 and 100!");
            }
            project.setProgress(request.progress());
        }

        if (request.tasksId() != null) {

            List<Task> tasks = request.tasksId().stream()
                    .map(id -> taskRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Task with ID: " + id + " does not exist!"))
                    )
                    .toList();

            project.setTasks(tasks);
        }

        projectRepository.save(project);

        return projectMapper.toProjectResponse(project);
    }

    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project with ID: " + projectId + " does not exist!"));

        projectRepository.delete(project);
    }
}
