<template>
  <section class="hero">
    <div class="hero-content">
      <h1>Work Smarter, Achieve More</h1>
      <p>
        Streamline your tasks and enhance collaboration effortlessly with our
        platform.
      </p>
      <div class="hero-buttons">
        <button class="btn-primary" @click="redirectToRegister">
          Get Started
        </button>
        <button class="btn-secondary" @click="redirectToLogin">Login</button>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  methods: {
    redirectToRegister() {
      this.$router.push("/register");
    },
    redirectToLogin() {
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
.hero {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 80px 20px;
  padding-bottom: 0px;
}

.hero-content {
  max-width: 700px;
  margin-bottom: 40px;
}

.hero-content h1 {
  font-size: 72px;
  color: #094067;
  margin-bottom: 20px;
}

.hero-content p {
  font-size: 20px;
  color: #094067;
  margin-bottom: 40px;
}

.hero-buttons {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.btn-primary {
  background: linear-gradient(90deg, #094067, #051933);
  color: #fff;
  padding: 15px 25px;
  font-size: 1.2rem;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.btn-primary:hover {
  background: linear-gradient(90deg, #051933, #094067);
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.btn-secondary {
  background: transparent;
  color: #094067;
  padding: 15px 25px;
  font-size: 1.2rem;
  border: 2px solid #094067;
  border-radius: 20px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease, box-shadow 0.3s ease;
}

.btn-secondary:hover {
  background: #dbe4f0;
  color: #051933;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
