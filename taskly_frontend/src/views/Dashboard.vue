<template>
  <div class="dashboard-container">
    <Navbar />
    <main class="dashboard">
      <ReportsSection :stats="stats" />
      <ProjectsSection :projects="projects" />
      <TasksSection :tasks="tasks" />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import ProjectsSection from "../components/dashboardView/ProjectsSection.vue";
import TasksSection from "../components/dashboardView/TasksSection.vue";
import ReportsSection from "../components/dashboardView/ReportsSection.vue";

export default {
  components: {
    Navbar,
    Footer,
    ProjectsSection,
    TasksSection,
    ReportsSection,
  },
  data() {
    return {
      projects: [
        { name: "Projeto A", progress: 50 },
        { name: "Projeto B", progress: 80 },
        { name: "Projeto C", progress: 30 },
      ],
      tasks: [
        { name: "Finalizar design", deadline: "2024-01-15" },
        { name: "Revisar código", deadline: "2024-01-20" },
        { name: "Criar apresentação", deadline: "2024-01-25" },
      ],
      stats: {
        totalProjects: 10,
        completedTasks: 40,
        pendingTasks: 5,
      },
    };
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
