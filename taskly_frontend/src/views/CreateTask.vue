<template>
  <div class="task-container">
    <Navbar />
    <main class="task">
      <CreateTask />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import CreateTask from "../components/projectsView/CreateTask.vue";

export default {
  components: {
    Navbar,
    Footer,
    CreateTask,
  },
};
</script>

<style scoped>
.task-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.task {
  flex: 1;
}
</style>
