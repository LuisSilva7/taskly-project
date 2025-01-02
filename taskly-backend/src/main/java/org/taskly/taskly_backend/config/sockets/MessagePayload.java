package org.taskly.taskly_backend.config.sockets;

public class MessagePayload {

    private String content;  // Conteúdo da mensagem
    private Long userId;   // ID do usuário que está enviando a mensagem

    // Construtores, Getters e Setters
    public MessagePayload() {}

    public MessagePayload(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
