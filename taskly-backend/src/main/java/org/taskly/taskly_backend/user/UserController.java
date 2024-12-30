package org.taskly.taskly_backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.taskly.taskly_backend.common.ApiResponse;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<UserResponse>> findUser(Authentication connectedUser) {
        UserResponse response = userService.findUser(connectedUser);

        return ResponseEntity.ok(new ApiResponse<>(
                "User with ID: " + response.id() + " obtained successfully!", response));
    }

    @PutMapping("/user")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            Authentication connectedUser,
            @ModelAttribute UserRequest request,
            @RequestParam(value = "photoUrl", required = false) MultipartFile photoUrl
    ) throws IOException {
        UserResponse response = userService.updateUser(connectedUser, request, photoUrl);

        return ResponseEntity.ok(new ApiResponse<>(
                "User with ID: " + response.id() + " updated successfully!", response));
    }
}
