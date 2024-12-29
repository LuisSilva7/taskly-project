package org.taskly.taskly_backend.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.taskly.taskly_backend.comment.Comment;
import org.taskly.taskly_backend.common.BaseEntity;
import org.taskly.taskly_backend.project.Project;
import org.taskly.taskly_backend.user.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Task extends BaseEntity {

    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private int estimatedTime;
    @ElementCollection
    @CollectionTable(name = "task_attachments", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "attatchment_url")
    private List<String> attachmentsUrl;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToMany(mappedBy = "userTasks")
    private List<User> taskMembers;

    @OneToMany(mappedBy = "task")
    private List<Comment> taskComments;

    //@OneToMany(mappedBy = "task")
    //private List<Notification> notifications;  // Lista de notificações associadas à tarefa
}
