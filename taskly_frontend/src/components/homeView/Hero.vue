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
        <button class="btn-secondary" @click="redirectToLogin">
          See a Demo
        </button>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "Hero",
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
  background-color: #094067;
  color: #fff;
  padding: 15px 25px;
  font-size: 1.2rem;
  border: none;
  border-radius: 20px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: #051933;
}

.btn-secondary {
  background-color: transparent;
  color: #094067;
  padding: 15px 25px;
  font-size: 1.2rem;
  border: 2px solid #094067;
  border-radius: 20px;
  cursor: pointer;
}

.btn-secondary:hover {
  background-color: #bcd9f5;
}
</style>
