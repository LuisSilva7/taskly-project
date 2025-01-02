package org.taskly.taskly_backend.config.sockets;

import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.message.Message;
import org.taskly.taskly_backend.message.MessageRepository;
import org.taskly.taskly_backend.user.User;
import org.taskly.taskly_backend.user.UserRepository;

@Component
@RequiredArgsConstructor
public class WebSocketHandlerImpl extends TextWebSocketHandler {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            // Converter a mensagem recebida para objeto MessagePayload
            ObjectMapper objectMapper = new ObjectMapper();
            MessagePayload payload = objectMapper.readValue(message.getPayload(), MessagePayload.class);

            // Salvar a mensagem no banco de dados
            Message newMessage = new Message();
            newMessage.setContent(payload.getContent());
            User user = userRepository.findById(payload.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "User with ID: " + payload.getUserId() + " does not exist!"));
            newMessage.setUser(user);
            newMessage.setCreatedBy(user.getEmail());
            messageRepository.save(newMessage);

            // Enviar a mensagem de volta para todos os clientes conectados
            TextMessage returnMessage = new TextMessage(objectMapper.writeValueAsString(payload));
            session.sendMessage(returnMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

