<template>
  <div class="chat-container">
    <Navbar />
    <main class="chat">
      <Chat />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import Chat from "../components/chatView/Chat.vue";

export default {
  components: {
    Navbar,
    Footer,
    Chat,
  },
};
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.chat {
  flex: 1;
}
</style>
