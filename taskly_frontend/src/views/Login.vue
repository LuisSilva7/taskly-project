<template>
  <div class="login-container">
    <Navbar />
    <main class="login">
      <Login />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import Login from "../components/loginView/Login.vue";

export default {
  components: {
    Navbar,
    Footer,
    Login,
  },
};
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.login {
  flex: 1;
}
</style>
