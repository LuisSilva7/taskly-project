package org.taskly.taskly_backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.taskly.taskly_backend.config.security.JwtService;
import org.taskly.taskly_backend.exception.custom.ResourceAlreadyExistsException;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.role.RoleRepository;
import org.taskly.taskly_backend.user.User;
import org.taskly.taskly_backend.user.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegistrationRequest request, MultipartFile photoFile) throws IOException {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email " + request.email() + " already exists!");
        }

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role with name: USER not found!"));

        var user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .dateOfBirth(request.dateOfBirth())
                .jobRole(request.jobRole())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(userRole))
                .build();

        if (photoFile == null || photoFile.isEmpty()) {
            user.setPhotoUrl("http://localhost:8888/uploads/users/user-default.png");
        } else {
            String fileExtension = getFileExtension(photoFile);
            String fileName = "user-" + UUID.randomUUID() + "." + fileExtension;

            Path uploadPath = Path.of("src/main/resources/static/uploads/users");

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(photoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            user.setPhotoUrl("http://localhost:8888/uploads/users/" + fileName);
        }

        userRepository.save(user);
    }

    private String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "jpg";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (userRepository.findByEmail(request.email()).isEmpty()) {
            throw new ResourceNotFoundException("Email " + request.email() + " not found!");
        }

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullname());
        var jwtToken = jwtService.generateToken(claims, user);
        return new AuthenticationResponse(jwtToken, user.getFirstname(), user.getPhotoUrl());
    }
}