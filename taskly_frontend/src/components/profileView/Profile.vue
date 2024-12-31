<template>
  <div class="profile-form">
    <h2>Edit Profile</h2>
    <form @submit.prevent="handleProfileUpdate">
      <div class="form-group">
        <label for="firstname">First Name</label>
        <input
          type="text"
          id="firstname"
          v-model="firstname"
          required
          :placeholder="firstname || 'Enter your first name'"
        />
      </div>
      <div class="form-group">
        <label for="lastname">Last Name</label>
        <input
          type="text"
          id="lastname"
          v-model="lastname"
          required
          :placeholder="lastname || 'Enter your last name'"
        />
      </div>
      <div class="form-group">
        <label for="jobRole">Job Role</label>
        <input
          type="text"
          id="jobRole"
          v-model="jobRole"
          required
          :placeholder="jobRole || 'Enter your job role'"
        />
      </div>
      <div class="form-group">
        <label for="dateOfBirth">Date of Birth</label>
        <input
          type="date"
          id="dateOfBirth"
          v-model="dateOfBirth"
          required
          :placeholder="dateOfBirth || 'Enter your date of birth'"
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
      <button type="submit" class="btn-save">Save Changes</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      firstname: "",
      lastname: "",
      jobRole: "",
      email: "",
      dateOfBirth: "",
      photoUrl: null,
      photoPreview: "",
    };
  },
  created() {
    this.fetchProfile();
  },
  methods: {
    async fetchProfile() {
      try {
        const token = localStorage.getItem("auth_token");
        if (!token) {
          alert("You must be logged in to view your profile.");
          this.$router.push("/login");
        }
        const response = await axios.get("/api/v1/users/user", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        const { firstname, lastname, jobRole, email, photoUrl, dateOfBirth } =
          response.data.data;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jobRole = jobRole;
        this.email = email;
        this.photoUrl = photoUrl;
        this.dateOfBirth = dateOfBirth;
      } catch (error) {
        console.error("Error fetching profile data:", error);
        alert("Failed to load profile data.");
      }
    },
    async handleProfileUpdate() {
      const formData = new FormData();
      formData.append("firstname", this.firstname);
      formData.append("lastname", this.lastname);
      formData.append("jobRole", this.jobRole);
      formData.append("email", this.email);
      formData.append("dateOfBirth", this.dateOfBirth);
      formData.append("photoUrl", this.photoUrl);
      const token = localStorage.getItem("auth_token");
      try {
        const response = await axios.put("/api/v1/users/user", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.status === 200) {
          alert("Profile updated successfully!");
        } else {
          alert("Profile update failed. Please try again.");
        }
      } catch (error) {
        console.error("Error updating profile:", error);
        alert("Profile update failed. Please try again.");
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
.profile-form {
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

.image-preview {
  max-width: 100%;
  max-height: 150px;
  margin-top: 10px;
  display: block;
  border-radius: 5px;
}
</style>
