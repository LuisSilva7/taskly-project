<template>
  <section class="login">
    <div class="login-content">
      <h1>Welcome Back!</h1>
      <p>Login to your account and start managing your tasks.</p>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="email">Email</label>
          <input
            type="email"
            id="email"
            v-model="email"
            placeholder="Enter your email"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input
            type="password"
            id="password"
            v-model="password"
            placeholder="Enter your password"
            required
          />
        </div>
        <div class="form-actions">
          <button type="submit" class="btn-primary">Login</button>
          <p class="register-link">
            Don't have an account?
            <router-link to="/register">Sign Up</router-link>
          </p>
        </div>
      </form>
    </div>
    <div class="default-credentials">
      <p>Default email: silva@gmail.com</p>
      <p>Default password: 12345</p>
    </div>
  </section>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    async handleLogin() {
      const credentials = {
        email: this.email,
        password: this.password,
      };

      try {
        const response = await axios.post(
          "/api/v1/auth/authenticate",
          credentials
        );

        if (response.data && response.data.data.token) {
          localStorage.setItem("auth_token", response.data.data.token);

          const username = response.data.data.firstname;
          const photoUrl = response.data.data.photoUrl;
          localStorage.setItem("username", username);
          localStorage.setItem("photoUrl", photoUrl);

          const tokenExpiration = 8640000;
          localStorage.setItem(
            "token_expiration",
            Date.now() + tokenExpiration
          );

          this.$router.push("/dashboard");
        }
      } catch (error) {
        console.error("Error with login: ", error);
        alert("Login failed!");
      }
    },
  },
};
</script>

<style scoped>
.login {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 80px;
  padding: 20px;
}

.login-content {
  max-width: 500px;
  width: 100%;
  background-color: #fff;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.login-content h1 {
  font-size: 36px;
  color: #094067;
  margin-bottom: 20px;
}

.login-content p {
  font-size: 16px;
  color: #094067;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

.form-group label {
  font-size: 14px;
  color: #3a4a62;
}

.form-group input {
  width: 100%;
  padding: 12px;
  margin-top: 8px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  color: #3a4a62;
}

.form-group input:focus {
  outline: none;
  border-color: #f27d33;
  background-color: #fff;
}

.form-actions {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.btn-primary {
  background-color: #094067;
  color: #fff;
  padding: 15px 25px;
  font-size: 1.2rem;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.btn-primary:hover {
  background-color: #051933;
  transform: scale(1.05);
}

.register-link {
  font-size: 14px;
  color: #3a4a62;
}

.register-link a {
  color: #f27d33;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}

.default-credentials {
  color: red;
  margin-top: -50px;
}
</style>
