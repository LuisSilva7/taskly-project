<template>
  <div class="registration-form">
    <h2>Register</h2>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="firstname">First Name</label>
        <input
          type="text"
          id="firstname"
          v-model="firstname"
          required
          placeholder="Enter your first name"
        />
      </div>
      <div class="form-group">
        <label for="lastname">Last Name</label>
        <input
          type="text"
          id="lastname"
          v-model="lastname"
          required
          placeholder="Enter your last name"
        />
      </div>
      <div class="form-group">
        <label for="dateOfBirth">Date of Birth</label>
        <input type="date" id="dateOfBirth" v-model="dateOfBirth" required />
      </div>

      <div class="form-group">
        <label for="jobRole">Job Role</label>
        <input
          type="text"
          id="jobRole"
          v-model="jobRole"
          required
          placeholder="Enter your job role"
        />
      </div>
      <div class="form-group">
        <label for="photoUrl">Photo</label>
        <input type="file" id="photoUrl" @change="handleFileChange" />
        <div v-if="photoPreview">
          <p>Preview:</p>
          <img
            :src="photoPreview"
            alt="Profile Image Preview"
            class="image-preview"
          />
        </div>
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input
          type="email"
          id="email"
          v-model="email"
          required
          placeholder="Enter your email"
        />
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input
          type="password"
          id="password"
          v-model="password"
          required
          placeholder="Enter your password"
        />
      </div>
      <button type="submit" class="btn-register">Register</button>
    </form>
    <div class="login-option">
      <p>
        Already have an account?
        <router-link to="/login">Login here</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      firstname: "",
      lastname: "",
      dateOfBirth: "",
      jobRole: "",
      photoUrl: null,
      email: "",
      password: "",
      photoPreview: "",
    };
  },
  methods: {
    async handleRegister() {
      const formData = new FormData();
      formData.append("firstname", this.firstname);
      formData.append("lastname", this.lastname);
      formData.append("dateOfBirth", this.dateOfBirth);
      formData.append("jobRole", this.jobRole);
      formData.append("email", this.email);
      formData.append("password", this.password);
      if (this.photoUrl) {
        formData.append("photoUrl", this.photoUrl);
      }
      try {
        const response = await axios.post("/api/v1/auth/register", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        if (response.status === 200) {
          alert("Registration successful!");
          this.$router.push("/login");
        } else {
          alert("Registration failed. Please try again.");
        }
      } catch (error) {
        console.error("Error with registration:", error);
        alert("Registration failed. Please try again.");
      }
    },
    handleFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        this.photoUrl = file;
        const reader = new FileReader();

        reader.onload = () => {
          this.photoPreview = reader.result;
        };
        reader.readAsDataURL(file);
      }
    },
  },
};
</script>

<style scoped>
.registration-form {
  width: 100%;
  max-width: 500px;
  margin: 50px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  font-size: 1.1rem;
  margin-bottom: 5px;
}

input {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 5px;
}

input[type="date"] {
  cursor: pointer;
}

button {
  display: block;
  width: 100%;
  padding: 12px;
  background-color: #094067;
  color: white;
  font-size: 1.1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #073b4c;
}

.login-option {
  margin-top: 20px;
  text-align: center;
}

.login-option p {
  font-size: 1rem;
}

.login-option a {
  color: #f27d33;
  text-decoration: none;
}

.login-option a:hover {
  text-decoration: underline;
}

.image-preview {
  max-width: 100%;
  max-height: 150px;
  margin-top: 10px;
  display: block;
  border-radius: 5px;
}
</style>
