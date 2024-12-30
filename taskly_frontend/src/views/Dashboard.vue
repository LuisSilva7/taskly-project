<template>
  <div class="dashboard-container">
    <Navbar />
    <main class="dashboard">
      <Dashboard />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import Dashboard from "../components/dashboardView/Dashboard.vue";

export default {
  components: {
    Navbar,
    Footer,
    Dashboard,
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
