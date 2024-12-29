package org.taskly.taskly_backend.project;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.taskly.taskly_backend.common.BaseEntity;
import org.taskly.taskly_backend.project.enums.ProjectCategory;
import org.taskly.taskly_backend.project.enums.ProjectPriority;
import org.taskly.taskly_backend.project.enums.ProjectStatus;
import org.taskly.taskly_backend.task.Task;
import org.taskly.taskly_backend.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Project extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal budget;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate endDate;
    private Integer progress;
    @ElementCollection
    @CollectionTable(name = "project_attachments", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "attatchment_url")
    private List<String> attachmentsUrl;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    @Enumerated(EnumType.STRING)
    private ProjectCategory category;
    @Enumerated(EnumType.STRING)
    private ProjectPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_manager_id")
    private User projectManager;

    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> projectMembers;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    //private List<Message> messages;
}
