package org.taskly.taskly_backend.user;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.taskly.taskly_backend.common.PageResponse;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public PageResponse<UserResponse> findAllUsers(int pageNumber, int pageSize) {
        pageNumber = Math.max(0, pageNumber);
        pageSize = pageSize > 0 ? pageSize : 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<User> users = userRepository.findAllUsers(pageable);

        List<UserResponse> response = users.stream()
                .map(userMapper::toUserResponse)
                .toList();

        return new PageResponse<>(
                response,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
    }

    public UserResponse findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with ID: " + userId + " does not exist!"));

        return userMapper.toUserResponse(user);
    }

    public UserResponse findUser(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(Authentication connectedUser, UserRequest request,
                                   MultipartFile photoFile) throws IOException {
        User user = ((User) connectedUser.getPrincipal());

        if(request.firstname() != null) {
            user.setFirstname(request.firstname());
        }

        if(request.lastname() != null) {
            user.setLastname(request.lastname());
        }

        if(request.dateOfBirth() != null) {
            if (request.dateOfBirth().isAfter(user.getDateOfBirth())) {
                throw new IllegalArgumentException("Date of birth cannot be in the future!");
            }
            user.setDateOfBirth(request.dateOfBirth());
        }

        if(request.jobRole() != null) {
            if (request.jobRole().length() < 2 || request.jobRole().length() > 40) {
                throw new IllegalArgumentException("Job role must be between 2 and 40 characters!");
            }
            user.setJobRole(request.jobRole());
        }

        if(photoFile != null) {
            String fileExtension = getFileExtension(photoFile);
            String fileName = "user-" + UUID.randomUUID() + "." + fileExtension;

            Path uploadPath = Path.of("src/main/resources/static/uploads/users");

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(photoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            user.setPhotoUrl("http://localhost:8888/uploads/users/" + fileName);
        }


        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

private String getFileExtension(MultipartFile file) {
    String fileName = file.getOriginalFilename();
    if (fileName != null && fileName.contains(".")) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    return "jpg";
}
}
