<template>
  <div class="dashboard-container">
    <Navbar />
    <main class="dashboard">
      <StatisticsSection />
      <ProjectsSection />
      <TasksSection />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import ProjectsSection from "../components/dashboardView/ProjectsSection.vue";
import TasksSection from "../components/dashboardView/TasksSection.vue";
import StatisticsSection from "../components/dashboardView/StatisticsSection.vue";

export default {
  components: {
    Navbar,
    Footer,
    ProjectsSection,
    TasksSection,
    StatisticsSection,
  },
};
</script>

<style scoped>
.dashboard-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.dashboard {
  flex: 1;
}
</style>
