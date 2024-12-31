package org.taskly.taskly_backend.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.taskly.taskly_backend.user.User;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId")
    Page<Task> findAllTasksByProjectId(Pageable pageable, @Param("projectId") Long projectId);

    @Query("SELECT t FROM Task t JOIN t.taskMembers u WHERE u.id = :userId")
    Page<Task> findAllTasksByUser(Pageable pageable, @Param("userId") Long userId);

    @Query("SELECT COUNT(t) FROM Task t WHERE :user MEMBER OF t.taskMembers")
    Integer countTasksByUserId(@Param("user") User user);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = 'COMPLETED' AND :user MEMBER OF t.taskMembers")
    Integer countCompletedTasksByUserId(@Param("user") User user);
}
