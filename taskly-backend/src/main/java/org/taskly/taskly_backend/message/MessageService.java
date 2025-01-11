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

    public void saveMessage(Message message) {
        User user = userRepository.findById(message.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with ID: " + message.getUser().getId() + " does not exist!"));

        messageRepository.save(
                Message.builder()
                        .content(message.getContent())
                        .user(user)
                        .build()
        );
    }
}
