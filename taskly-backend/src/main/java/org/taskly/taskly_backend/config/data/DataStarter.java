package org.taskly.taskly_backend.config.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.role.Role;
import org.taskly.taskly_backend.role.RoleRepository;
import org.taskly.taskly_backend.user.User;
import org.taskly.taskly_backend.user.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class DataStarter implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        List<String> roles = Arrays.asList("ADMIN", "MANAGER", "USER");

        for (String roleName : roles) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Role " + roleName + " created!");
            }
        }

        var adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("Role with name: ADMIN not found!"));

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role with name: USER not found!"));

        if(userRepository.findAll().isEmpty()) {
            // relacoes
            var admin = User.builder()
                    .firstname("Luís")
                    .lastname("Silva")
                    .dateOfBirth(LocalDate.parse("2003-08-18"))
                    .jobRole("Software Engineer")
                    .photoUrl("http://localhost:8888/uploads/users/user-default.png")
                    .email("silva@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(adminRole))
                    .build();

            userRepository.save(admin);

            // relacoes
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

            // relacoes
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

            // relacoes
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
        }
    }
}
