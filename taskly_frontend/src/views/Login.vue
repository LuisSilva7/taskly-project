<template>
  <div class="home-container">
    <Navbar />
    <main class="home">
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
  name: "Home",
  components: {
    Navbar,
    Footer,
    Login,
  },
};
</script>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.home {
  flex: 1;
}
</style>
