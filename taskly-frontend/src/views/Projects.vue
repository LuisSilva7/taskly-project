<template>
  <div class="projects-container">
    <Navbar />
    <main class="projects">
      <Projects />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import Projects from "../components/projectsView/Projects.vue";

export default {
  components: {
    Navbar,
    Footer,
    Projects,
  },
};
</script>

<style scoped>
.projects-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.projects {
  flex: 1;
}
</style>
