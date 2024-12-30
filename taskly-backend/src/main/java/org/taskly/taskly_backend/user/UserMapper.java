package org.taskly.taskly_backend.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getDateOfBirth(),
                user.getJobRole()
        );
    }
}
