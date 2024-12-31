package org.taskly.taskly_backend.config.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.taskly.taskly_backend.comment.Comment;
import org.taskly.taskly_backend.comment.CommentRepository;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.project.Project;
import org.taskly.taskly_backend.project.ProjectRepository;
import org.taskly.taskly_backend.role.Role;
import org.taskly.taskly_backend.role.RoleRepository;
import org.taskly.taskly_backend.task.Task;
import org.taskly.taskly_backend.task.TaskRepository;
import org.taskly.taskly_backend.task.TaskStatus;
import org.taskly.taskly_backend.user.User;
import org.taskly.taskly_backend.user.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.taskly.taskly_backend.project.enums.ProjectCategory.*;
import static org.taskly.taskly_backend.project.enums.ProjectPriority.HIGH;
import static org.taskly.taskly_backend.project.enums.ProjectPriority.MEDIUM;
import static org.taskly.taskly_backend.project.enums.ProjectStatus.IN_PROGRESS;

@RequiredArgsConstructor
@Configuration
public class DataStarter implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        List<String> roles = Arrays.asList("ADMIN", "MANAGER", "USER");

        for (String roleName : roles) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }

        var adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("Role with name: ADMIN not found!"));

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role with name: USER not found!"));

        if(userRepository.findAll().isEmpty()) {
            var admin = User.builder()
                    .firstname("Luís")
                    .lastname("Silva")
                    .dateOfBirth(LocalDate.parse("2003-08-18"))
                    .jobRole("Software Engineer")
                    .photoUrl("http://localhost:8888/uploads/users/user-98f0c9cc-9c24-4596-b267-f79c078cf53a.png")
                    .email("silva@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(adminRole))
                    .build();

            userRepository.save(admin);

            var user1 = User.builder()
                    .firstname("André")
                    .lastname("Gomes")
                    .dateOfBirth(LocalDate.parse("2000-01-01"))
                    .jobRole("Software Engineer")
                    .photoUrl("http://localhost:8888/uploads/users/user-default.png")
                    .email("gomes@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(userRole))
                    .build();

            userRepository.save(user1);

            var user2 = User.builder()
                    .firstname("Joana")
                    .lastname("Faria")
                    .dateOfBirth(LocalDate.parse("2004-03-21"))
                    .jobRole("UI Designer")
                    .photoUrl("http://localhost:8888/uploads/users/user-default.png")
                    .email("faria@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(userRole))
                    .build();

            userRepository.save(user2);

            var user3 = User.builder()
                    .firstname("João")
                    .lastname("Rodrigues")
                    .dateOfBirth(LocalDate.parse("2005-12-22"))
                    .jobRole("Software Engineer")
                    .photoUrl("http://localhost:8888/uploads/users/user-default.png")
                    .email("rodrigues@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(userRole))
                    .build();

            userRepository.save(user3);

            var user4 = User.builder()
                    .firstname("Ana")
                    .lastname("Silva")
                    .dateOfBirth(LocalDate.parse("1990-04-15"))
                    .jobRole("IT Manager")
                    .photoUrl("http://localhost:8888/uploads/users/user-default.png")
                    .email("ana.silva@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(userRole))
                    .build();

            userRepository.save(user4);

            var user5 = User.builder()
                    .firstname("Carlos")
                    .lastname("Pereira")
                    .dateOfBirth(LocalDate.parse("1985-07-10"))
                    .jobRole("Data Analyst")
                    .photoUrl("http://localhost:8888/uploads/users/user-default.png")
                    .email("carlos.pereira@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(userRole))
                    .build();

            userRepository.save(user5);

            var user6 = User.builder()
                    .firstname("Maria")
                    .lastname("Costa")
                    .dateOfBirth(LocalDate.parse("1992-11-05"))
                    .jobRole("UX Designer")
                    .photoUrl("http://localhost:8888/uploads/users/user-default.png")
                    .email("maria.costa@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(userRole))
                    .build();

            userRepository.save(user6);

            var user7 = User.builder()
                    .firstname("Lucas")
                    .lastname("Mendes")
                    .dateOfBirth(LocalDate.parse("1988-01-30"))
                    .jobRole("System Administrator")
                    .photoUrl("http://localhost:8888/uploads/users/user-default.png")
                    .email("lucas.mendes@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(userRole))
                    .build();

            userRepository.save(user7);

            var user8 = User.builder()
                    .firstname("Fernanda")
                    .lastname("Almeida")
                    .dateOfBirth(LocalDate.parse("1995-09-12"))
                    .jobRole("Frontend Developer")
                    .photoUrl("http://localhost:8888/uploads/users/user-default.png")
                    .email("fernanda.almeida@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(userRole))
                    .build();

            userRepository.save(user8);
        }

        if(projectRepository.findAll().isEmpty()) {
            var project1 = Project.builder()
                    .name("Website Redesign")
                    .description("Revamping the company's website with a new design and improved functionality.")
                    .budget(new java.math.BigDecimal(50000))
                    .startDate(LocalDate.parse("2024-06-01"))
                    .expectedEndDate(LocalDate.parse("2025-06-01"))
                    .progress(45)
                    .status(IN_PROGRESS)
                    .category(DEVELOPMENT)
                    .priority(HIGH)
                    .projectManager(userRepository.findByEmail("silva@gmail.com")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "User with email: silva@gmail.com not found!")))
                    .projectMembers(List.of(
                            userRepository.findByEmail("silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: silva@gmail.com not found!")),
                            userRepository.findByEmail("gomes@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: gomes@gmail.com not found!")),
                            userRepository.findByEmail("faria@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: faria@gmail.com not found!")),
                            userRepository.findByEmail("rodrigues@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: rodrigues@gmail.com not found!")),
                            userRepository.findByEmail("ana.silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: ana.silva@gmail.com not found!"))))
                    .createdBy("silva@gmail.com")
                    .build();

            projectRepository.save(project1);

            var project2 = Project.builder()
                    .name("Mobile App Development")
                    .description("Developing a cross-platform mobile application.")
                    .budget(new java.math.BigDecimal(75000))
                    .startDate(LocalDate.parse("2024-05-01"))
                    .expectedEndDate(LocalDate.parse("2025-02-01"))
                    .progress(70)
                    .status(IN_PROGRESS)
                    .category(DEVELOPMENT)
                    .priority(MEDIUM)
                    .projectManager(userRepository.findByEmail("silva@gmail.com")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "User with email: silva@gmail.com not found!")))
                    .projectMembers(List.of(
                            userRepository.findByEmail("silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: silva@gmail.com not found!")),
                            userRepository.findByEmail("gomes@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: gomes@gmail.com not found!")),
                            userRepository.findByEmail("carlos.pereira@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: carlos.pereira@gmail.com not found!")),
                            userRepository.findByEmail("maria.costa@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: maria.costa@gmail.com not found!")),
                            userRepository.findByEmail("fernanda.almeida@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: fernanda.almeida@gmail.com not found!"))))
                    .createdBy("silva@gmail.com")
                    .build();

            projectRepository.save(project2);

            var project3 = Project.builder()
                    .name("E-commerce Marketing Campaign")
                    .description("Developing a marketing campaign for an online e-commerce platform.")
                    .budget(new java.math.BigDecimal(120000))
                    .startDate(LocalDate.parse("2024-07-15"))
                    .expectedEndDate(LocalDate.parse("2024-03-15"))
                    .progress(55)
                    .status(IN_PROGRESS)
                    .category(MARKETING)
                    .priority(HIGH)
                    .projectManager(userRepository.findByEmail("silva@gmail.com")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "User with email: silva@gmail.com not found!")))
                    .projectMembers(List.of(
                            userRepository.findByEmail("silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: silva@gmail.com not found!")),
                            userRepository.findByEmail("gomes@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: gomes@gmail.com not found!")),
                            userRepository.findByEmail("faria@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: faria@gmail.com not found!")),
                            userRepository.findByEmail("rodrigues@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: rodrigues@gmail.com not found!")),
                            userRepository.findByEmail("ana.silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: ana.silva@gmail.com not found!"))))
                    .createdBy("silva@gmail.com")
                    .build();

            projectRepository.save(project3);

            var project4 = Project.builder()
                    .name("Tech Infrastructure Overhaul")
                    .description("Overhauling the company's technology infrastructure.")
                    .budget(new java.math.BigDecimal(150000))
                    .startDate(LocalDate.parse("2024-03-01"))
                    .expectedEndDate(LocalDate.parse("2025-03-01"))
                    .progress(50)
                    .status(IN_PROGRESS)
                    .category(TECHNOLOGY)
                    .priority(MEDIUM)
                    .projectManager(userRepository.findByEmail("silva@gmail.com")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "User with email: silva@gmail.com not found!")))
                    .projectMembers(List.of(
                            userRepository.findByEmail("silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: silva@gmail.com not found!")),
                            userRepository.findByEmail("gomes@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: gomes@gmail.com not found!")),
                            userRepository.findByEmail("fernanda.almeida@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: fernanda.almeida@gmail.com not found!")),
                            userRepository.findByEmail("lucas.mendes@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: lucas.mendes@gmail.com not found!")),
                            userRepository.findByEmail("ana.silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: ana.silva@gmail.com not found!"))))
                    .createdBy("silva@gmail.com")
                    .build();

            projectRepository.save(project4);

            var project5 = Project.builder()
                    .name("E-commerce Platform Revamp")
                    .description("Revamping the company's e-commerce platform.")
                    .budget(new java.math.BigDecimal(200000))
                    .startDate(LocalDate.parse("2024-05-01"))
                    .expectedEndDate(LocalDate.parse("2025-05-01"))
                    .progress(30)
                    .status(IN_PROGRESS)
                    .category(TECHNOLOGY)
                    .priority(HIGH)
                    .projectManager(userRepository.findByEmail("silva@gmail.com")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "User with email: silva@gmail.com not found!")))
                    .projectMembers(List.of(
                            userRepository.findByEmail("silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: silva@gmail.com not found!")),
                            userRepository.findByEmail("gomes@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: gomes@gmail.com not found!")),
                            userRepository.findByEmail("fernanda.almeida@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: fernanda.almeida@gmail.com not found!")),
                            userRepository.findByEmail("lucas.mendes@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: lucas.mendes@gmail.com not found!")),
                            userRepository.findByEmail("ana.silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: ana.silva@gmail.com not found!"))))
                    .createdBy("silva@gmail.com")
                    .build();

            projectRepository.save(project5);
        }

        if(taskRepository.findAll().isEmpty()) {
            var task1 = Task.builder()
                    .title("Design Wireframes")
                    .description("Design wireframes for the new website.")
                    .deadline(LocalDate.parse("2025-02-15"))
                    .status(TaskStatus.IN_PROGRESS)
                    .project(projectRepository.findByName("Website Redesign")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Project with name: Website Redesign not found!")))
                    .taskMembers(List.of(
                            userRepository.findByEmail("silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: silva@gmail.com not found!")),
                            userRepository.findByEmail("gomes@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: gomes@gmail.com not found!")),
                            userRepository.findByEmail("faria@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: faria@gmail.com not found!")),
                            userRepository.findByEmail("rodrigues@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: rodrigues@gmail.com not found!")),
                            userRepository.findByEmail("ana.silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: ana.silva@gmail.com not found!"))))
                    .createdBy("silva@gmail.com")
                    .build();

            taskRepository.save(task1);

            var task2 = Task.builder()
                    .title("Design Landing Page")
                    .description("Create a responsive landing page for the new product.")
                    .deadline(LocalDate.parse("2025-03-03"))
                    .status(TaskStatus.IN_PROGRESS)
                    .project(projectRepository.findByName("Website Redesign")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Project with name: Website Redesign not found!")))
                    .taskMembers(List.of(
                            userRepository.findByEmail("gomes@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: gomes@gmail.com not found!")),
                            userRepository.findByEmail("ana.silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: ana.silva@gmail.com not found!"))))
                    .createdBy("silva@gmail.com")
                    .build();

            taskRepository.save(task2);

            var task3 = Task.builder()
                    .title("Develop API Endpoints")
                    .description("Implement the necessary API endpoints for user authentication and data retrieval.")
                    .deadline(LocalDate.parse("2025-03-15"))
                    .status(TaskStatus.IN_PROGRESS)
                    .project(projectRepository.findByName("Website Redesign")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Project with name: Website Redesign not found!")))
                    .taskMembers(List.of(
                            userRepository.findByEmail("gomes@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: gomes@gmail.com not found!")),
                            userRepository.findByEmail("ana.silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: ana.silva@gmail.com not found!"))))
                    .createdBy("gomes@gmail.com")
                    .build();

            taskRepository.save(task3);

            var task4 = Task.builder()
                    .title("Create Marketing Strategy")
                    .description("Develop a comprehensive marketing plan for the product launch.")
                    .deadline(LocalDate.parse("2025-02-01"))
                    .status(TaskStatus.IN_PROGRESS)
                    .project(projectRepository.findByName("Website Redesign")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Project with name: Website Redesign not found!")))
                    .taskMembers(List.of(
                            userRepository.findByEmail("ana.silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: ana.silva@gmail.com not found!")),
                            userRepository.findByEmail("silva@gmail.com")
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "User with email: silva@gmail.com not found!"))))
                    .createdBy("ana.silva@gmail.com")
                    .build();

            taskRepository.save(task4);

        }

        if(commentRepository.findAll().isEmpty()) {
            var comment1 = Comment.builder()
                    .text("Let´s go team!")
                    .task(taskRepository.findByTitle("Design Wireframes")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Task with title: Website Redesign not found!")))
                    .createdBy("silva@gmail.com")
                    .build();

            commentRepository.save(comment1);

            var comment2 = Comment.builder()
                    .text("Great progress so far, keep it up!")
                    .task(taskRepository.findByTitle("Design Wireframes")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Task with title: Design Wireframes not found!")))
                    .createdBy("rodrigues@gmail.com")
                    .build();

            commentRepository.save(comment2);

            var comment3 = Comment.builder()
                    .text("Don’t forget to review the color scheme.")
                    .task(taskRepository.findByTitle("Design Wireframes")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Task with title: Design Wireframes not found!")))
                    .createdBy("faria@gmail.com")
                    .build();

            commentRepository.save(comment3);

            var comment4 = Comment.builder()
                    .text("What’s the deadline for the first draft?")
                    .task(taskRepository.findByTitle("Design Wireframes")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Task with title: Design Wireframes not found!")))
                    .createdBy("ana.silva@gmail.com")
                    .build();

            commentRepository.save(comment4);

            var comment5 = Comment.builder()
                    .text("What have you done André?")
                    .task(taskRepository.findByTitle("Design Landing Page")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Task with title: Design Landing Page not found!")))
                    .createdBy("ana.silva@gmail.com")
                    .build();

            commentRepository.save(comment5);

            var comment6 = Comment.builder()
                    .text("I´m going to start now!")
                    .task(taskRepository.findByTitle("Design Landing Page")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Task with title: Design Landing Page not found!")))
                    .createdBy("gomes@gmail.com")
                    .build();

            commentRepository.save(comment6);

            var comment7 = Comment.builder()
                    .text("Already finished everything!")
                    .task(taskRepository.findByTitle("Design Landing Page")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Task with title: Design Landing Page not found!")))
                    .createdBy("gomes@gmail.com")
                    .build();

            commentRepository.save(comment7);

            var comment8 = Comment.builder()
                    .text("Nice, I will review it!")
                    .task(taskRepository.findByTitle("Design Landing Page")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Task with title: Design Landing Page not found!")))
                    .createdBy("ana.silva@gmail.com")
                    .build();

            commentRepository.save(comment8);
        }
    }
}
