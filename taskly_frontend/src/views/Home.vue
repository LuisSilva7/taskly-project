<template>
  <div class="home-container">
    <Navbar />
    <main class="home">
      <p>
        Aqui você pode visualizar e gerenciar todos os seus projetos, tarefas e
        relatórios de forma simples e eficiente.
      </p>
    </main>
    <Footer />
  </div>
</template>

<script>
// Importando a Navbar e o Footer
import Navbar from "../components/shared/Navbar.vue";
import Footer from "../components/shared/Footer.vue";

export default {
  name: "Home",
  components: {
    Navbar,
    Footer,
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
