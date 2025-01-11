<template>
  <div class="register-container">
    <Navbar />
    <main class="register">
      <Register />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import Register from "../components/registerView/Register.vue";

export default {
  components: {
    Navbar,
    Footer,
    Register,
  },
};
</script>

<style scoped>
.register-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.register {
  flex: 1;
}
</style>
