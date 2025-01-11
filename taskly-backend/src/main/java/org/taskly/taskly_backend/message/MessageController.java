package org.taskly.taskly_backend.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public Message handleMessage(Message message) {
        messageService.saveMessage(message);
        return message;
    }
}
