package org.taskly.taskly_backend.config.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.taskly.taskly_backend.role.Role;
import org.taskly.taskly_backend.role.RoleRepository;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class DataStarter implements CommandLineRunner {

    private final RoleRepository roleRepository;

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
    }
}
