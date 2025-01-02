package org.taskly.taskly_backend.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.user.User;
import org.taskly.taskly_backend.user.UserRepository;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public Message saveMessage(String content, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with ID: " + userId + " does not exist!"));

        return messageRepository.save(
                Message.builder()
                .content(content)
                .user(user)
                .build()
        );
    }
}
