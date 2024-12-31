<template>
  <div class="projects-container">
    <Navbar />
    <main class="projects">
      <ProjectInfo :projectId="projectId" />
      <ProjectTasks :projectId="projectId" />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import ProjectInfo from "../components/projectsView/ProjectInfo.vue";
import ProjectTasks from "../components/projectsView/ProjectTasks.vue";

export default {
  data() {
    return {
      projectId: null,
    };
  },
  components: {
    Navbar,
    Footer,
    ProjectInfo,
    ProjectTasks,
  },
  created() {
    const projectId = this.$route.params.projectId;
    if (projectId) {
      this.projectId = projectId;
    }
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
