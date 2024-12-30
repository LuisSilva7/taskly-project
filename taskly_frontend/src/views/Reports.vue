<template>
  <div class="reports-container">
    <Navbar />
    <main class="reports">
      <Reports />
    </main>
    <Footer />
  </div>
</template>

<script>
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";
import Reports from "../components/reportsView/Reports.vue";

export default {
  components: {
    Navbar,
    Footer,
    Reports,
  },
};
</script>

<style scoped>
.reports-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.reports {
  flex: 1;
}
</style>
