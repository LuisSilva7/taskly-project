package org.taskly.taskly_backend.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId")
    Page<Task> findAllTasksByProjectId(Pageable pageable, @Param("projectId") Long projectId);
}
