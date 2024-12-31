package org.taskly.taskly_backend.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.taskly.taskly_backend.user.User;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByName(String plate);

    @Query("SELECT project FROM Project project")
    Page<Project> findAllProjects(Pageable pageable);

    @Query("SELECT COUNT(p) FROM Project p WHERE :user MEMBER OF p.projectMembers")
    Integer countProjectsByUserId(@Param("user") User user);

    @Query("SELECT COUNT(p) FROM Project p WHERE p.status = 'COMPLETED' AND :user MEMBER OF p.projectMembers")
    Integer countCompletedProjectsByUserId(@Param("user") User user);
}
