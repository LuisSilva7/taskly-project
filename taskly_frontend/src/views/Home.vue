<template>
  <div class="home-container">
    <Navbar />
    <main class="home">
      <Hero />
      <Features />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import Hero from "../components/homeView/Hero.vue";
import Features from "../components/homeView/Features.vue";

export default {
  components: {
    Navbar,
    Footer,
    Hero,
    Features,
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
