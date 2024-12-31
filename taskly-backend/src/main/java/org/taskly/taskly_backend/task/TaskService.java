package org.taskly.taskly_backend.task;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.taskly.taskly_backend.common.PageResponse;
import org.taskly.taskly_backend.exception.custom.ResourceAlreadyExistsException;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.project.Project;
import org.taskly.taskly_backend.project.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public Long createTaskByProjectId(TaskRequest request, Long projectId) {
        if(taskRepository.findByTitle(request.title()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Task with title: " + request.title() + " already exists!");
        }

        return taskRepository.save(
                taskMapper.toTaskEntity(request, projectId)
        ).getId();
    }

    public PageResponse<TaskResponse> findAllTasksByProjectId(int pageNumber, int pageSize, Long projectId) {
        pageNumber = Math.max(0, pageNumber);
        pageSize = pageSize > 0 ? pageSize : 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());

        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()) {
            throw new ResourceNotFoundException("Project with ID: " + projectId + " does not exist!");
        }

        Page<Task> tasks = taskRepository.findAllTasksByProjectId(pageable, project.get().getId());

        List<TaskResponse> response = tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();

        return new PageResponse<>(
                response,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()
        );
    }
}
