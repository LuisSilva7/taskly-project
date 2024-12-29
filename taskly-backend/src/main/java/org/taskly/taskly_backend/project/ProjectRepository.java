package org.taskly.taskly_backend.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByName(String plate);

    @Query("SELECT project FROM Project project")
    Page<Project> findAllProjects(Pageable pageable);
}
