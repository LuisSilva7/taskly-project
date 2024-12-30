<template>
  <div class="projects-container">
    <Navbar />
    <main class="projects">
      <CreateProject />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import CreateProject from "../components/projectsView/CreateProject.vue";

export default {
  components: {
    Navbar,
    Footer,
    CreateProject,
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
