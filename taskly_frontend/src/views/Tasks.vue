<template>
  <div class="tasks-container">
    <Navbar />
    <main class="tasks">
      <Tasks />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import Tasks from "../components/tasksView/Tasks.vue";

export default {
  components: {
    Navbar,
    Footer,
    Tasks,
  },
};
</script>

<style scoped>
.tasks-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.tasks {
  flex: 1;
}
</style>
