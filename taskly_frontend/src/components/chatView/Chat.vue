<template>
  <div class="chat-container">
    <div class="chat-header">
      <h2>Chat Taskly</h2>
    </div>

    <div class="chat-messages" ref="messagesContainer">
      <div
        v-for="(message, index) in messages"
        :key="index"
        class="message"
        :class="{ sent: message.sentByUser, received: !message.sentByUser }"
      >
        <div class="message-content">{{ message.content }}</div>
      </div>
    </div>

    <div class="chat-input-container">
      <input
        type="text"
        v-model="messageInput"
        placeholder="Digite sua mensagem..."
        @keyup.enter="sendMessage"
      />
      <button @click="sendMessage">Enviar</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      messages: [], // Armazenar mensagens
      messageInput: "", // Input da mensagem
      ws: null, // Instância WebSocket
      userId: null, // ID do usuário logado
    };
  },
  methods: {
    // Conectar ao WebSocket
    connectWebSocket() {
      console.log("Tentando conectar ao WebSocket...");
      this.ws = new WebSocket("ws://localhost:8888/ws/chat");

      // Quando o WebSocket for aberto
      this.ws.onopen = () => {
        console.log("Conectado ao servidor WebSocket.");
      };

      // Tratar erros
      this.ws.onerror = (error) => {
        console.error("Erro WebSocket:", error);
        alert("Erro ao conectar com o servidor WebSocket.");
      };

      // Quando uma mensagem for recebida via WebSocket
      this.ws.onmessage = (event) => {
        const message = JSON.parse(event.data);
        this.messages.push({ content: message.content, sentByUser: false });
      };

      // Quando o WebSocket for fechado
      this.ws.onclose = () => {
        console.log("Desconectado do servidor WebSocket.");
      };
    },

    // Buscar informações do usuário
    async fetchUser() {
      try {
        const token = localStorage.getItem("auth_token"); // Buscar o token de autenticação
        const response = await axios.get(`/api/v1/users/user`, {
          headers: {
            Authorization: `Bearer ${token}`, // Enviar o token no cabeçalho
          },
        });
        this.userId = response.data.data.id; // Definir o userId
        console.log("ID do usuário:", this.userId);
      } catch (error) {
        console.error("Erro ao buscar o ID do usuário:", error);
      }
    },

    // Enviar uma mensagem via WebSocket
    sendMessage() {
      if (!this.userId) {
        alert("Erro: ID do usuário não encontrado.");
        return;
      }

      if (this.messageInput.trim() === "") return;

      // Criar o payload da mensagem
      const messagePayload = {
        content: this.messageInput,
        userId: this.userId,
      };

      // Enviar mensagem para o servidor WebSocket
      this.ws.send(JSON.stringify(messagePayload));

      // Adicionar a mensagem ao chat (enviada pelo usuário)
      this.messages.push({ content: this.messageInput, sentByUser: true });

      // Limpar o campo de input
      this.messageInput = "";

      // Rolagem automática para o final após envio
      this.$nextTick(() => {
        this.scrollToBottom();
      });
    },

    // Rolagem até o fundo das mensagens
    scrollToBottom() {
      const container = this.$refs.messagesContainer;
      container.scrollTop = container.scrollHeight;
    },
  },
  created() {
    this.fetchUser(); // Buscar o ID do usuário ao criar o componente
  },
  mounted() {
    this.connectWebSocket(); // Conectar ao WebSocket quando o componente for montado
  },
};
</script>

<style scoped>
.chat-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border-radius: 10px;
  background-color: #f4f7fb;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.chat-header {
  text-align: center;
  margin-bottom: 20px;
}

.chat-header h2 {
  color: #1f2937;
  font-family: "Arial", sans-serif;
  font-size: 24px;
}

.chat-messages {
  max-height: 400px;
  overflow-y: auto;
  margin-bottom: 20px;
}

.message {
  display: flex;
  margin-bottom: 15px;
  padding: 10px;
  border-radius: 12px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.sent {
  background-color: #3b82f6;
  color: white;
  justify-content: flex-end;
  align-self: flex-end;
}

.received {
  background-color: #f97316;
  color: white;
  justify-content: flex-start;
  align-self: flex-start;
}

.message-content {
  max-width: 80%;
  word-wrap: break-word;
}

.chat-input-container {
  display: flex;
  align-items: center;
}

.chat-input-container input {
  width: 80%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 25px;
  font-size: 16px;
}

.chat-input-container button {
  margin-left: 10px;
  padding: 10px 15px;
  background-color: #3b82f6;
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
}

.chat-input-container button:hover {
  background-color: #2563eb;
}
</style>
