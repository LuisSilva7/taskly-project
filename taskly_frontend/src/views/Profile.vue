<template>
  <div class="profile-container">
    <Navbar />
    <main class="profile">
      <Profile />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import Profile from "../components/profileView/Profile.vue";

export default {
  components: {
    Navbar,
    Footer,
    Profile,
  },
};
</script>

<style scoped>
.profile-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.profile {
  flex: 1;
}
</style>
