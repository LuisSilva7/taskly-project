<template>
  <section class="create-project">
    <h2>Create a New Project</h2>
    <form @submit.prevent="submitProject">
      <div class="form-group">
        <label for="name">Project Name</label>
        <input type="text" id="name" v-model="project.name" required />
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <textarea
          id="description"
          v-model="project.description"
          required
        ></textarea>
      </div>
      <div class="form-group">
        <label for="budget">Budget</label>
        <input
          type="number"
          id="budget"
          v-model.number="project.budget"
          required
        />
      </div>
      <div class="form-group">
        <label for="startDate">Start Date</label>
        <input
          type="date"
          id="startDate"
          v-model="project.startDate"
          required
        />
      </div>
      <div class="form-group">
        <label for="expectedEndDate">Expected End Date</label>
        <input
          type="date"
          id="expectedEndDate"
          v-model="project.expectedEndDate"
          required
        />
      </div>
      <div class="form-group">
        <label for="category">Category</label>
        <select id="category" v-model="project.category" required>
          <option value="TECHNOLOGY">Technology</option>
          <option value="DESIGN">Design</option>
          <option value="DEVELOPMENT">Development</option>
          <option value="MARKETING">Marketing</option>
          <option value="FINANCE">Finance</option>
          <option value="HUMAN_RESOURCES">Human Resources</option>
          <option value="OPERATIONS">Operations</option>
          <option value="SALES">Sales</option>
          <option value="RESEARCH">Research</option>
          <option value="EDUCATION">Education</option>
        </select>
      </div>
      <div class="form-group">
        <label for="priority">Priority</label>
        <select id="priority" v-model="project.priority" required>
          <option value="LOW">Low</option>
          <option value="MEDIUM">Medium</option>
          <option value="HIGH">High</option>
        </select>
      </div>
      <div class="form-group">
        <label for="projectManager">Project Manager</label>
        <div
          v-for="user in users"
          :key="user.id"
          class="user-item"
          :class="{ selected: project.projectManagerId === user.id }"
        >
          <span>
            {{ user.firstname }} {{ user.lastname }} - {{ user.jobRole }}
          </span>
          <button type="button" @click="addProjectManager(user.id)">
            {{ project.projectManagerId === user.id ? "Selected" : "+" }}
          </button>
        </div>
      </div>
      <div class="form-group">
        <label for="projectMembers">Project Members</label>
        <div
          v-for="user in users"
          :key="user.id"
          class="user-item"
          :class="{ selected: project.projectMembersId.includes(user.id) }"
        >
          <span>
            {{ user.firstname }} {{ user.lastname }} - {{ user.jobRole }}
          </span>
          <button type="button" @click="addProjectMember(user.id)">
            {{ project.projectMembersId.includes(user.id) ? "Selected" : "+" }}
          </button>
        </div>
      </div>
      <button type="submit" class="submit-btn">Create Project</button>
    </form>
  </section>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      project: {
        name: "",
        description: "",
        budget: null,
        startDate: "",
        expectedEndDate: "",
        status: "in_progress",
        category: "Technology",
        priority: "high",
        projectManagerId: null,
        projectMembersId: [],
      },
      users: [],
    };
  },
  created() {
    this.fetchUsers();
  },
  methods: {
    async fetchUsers() {
      try {
        const token = localStorage.getItem("auth_token");
        const response = await axios.get("/api/v1/users", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        this.users = response.data.data.content;
      } catch (error) {
        console.error("Erro ao buscar usuários:", error);
      }
    },
    addProjectManager(userId) {
      this.project.projectManagerId = userId;
    },
    addProjectMember(userId) {
      const index = this.project.projectMembersId.indexOf(userId);
      if (index > -1) {
        this.project.projectMembersId.splice(index, 1);
      } else {
        this.project.projectMembersId.push(userId);
      }
    },
    async submitProject() {
      try {
        if (
          this.project.projectManagerId &&
          !this.project.projectMembersId.includes(this.project.projectManagerId)
        ) {
          this.project.projectMembersId.push(this.project.projectManagerId);
        }
        const projectData = {
          name: this.project.name,
          description: this.project.description,
          budget: this.project.budget,
          startDate: this.project.startDate,
          expectedEndDate: this.project.expectedEndDate,
          status: this.project.status,
          category: this.project.category,
          priority: this.project.priority,
          projectManagerId: this.project.projectManagerId,
          projectMembersId: this.project.projectMembersId,
        };
        const token = localStorage.getItem("auth_token");
        await axios.post("/api/v1/projects", projectData, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        alert("Project created successfully!");
        this.$router.push("/projects");
      } catch (error) {
        console.error("Error creating project:", error);
        alert("Error creating project.");
      }
    },
  },
};
</script>

<style scoped>
.create-project {
  padding: 40px;
  border-radius: 12px;
  background-color: #f5faff; /* Azul muito claro */
  width: 70%;
  margin: 30px auto;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 2.5rem;
  color: #2c3e50;
  text-align: center;
  margin-bottom: 40px;
  font-weight: bold;
}

.form-group {
  margin-bottom: 25px;
}

label {
  font-size: 1rem;
  color: #34495e;
  font-weight: bold;
  display: block;
  margin-bottom: 8px;
}

input,
textarea,
select {
  width: 100%;
  padding: 15px;
  font-size: 1rem;
  border: 1px solid #ccd5e1;
  border-radius: 8px;
  margin-top: 5px;
  background-color: #ffffff;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

input:focus,
textarea:focus,
select:focus {
  outline: none;
  border-color: #2980b9;
  box-shadow: 0 0 8px rgba(41, 128, 185, 0.3);
}

textarea {
  height: 120px;
  resize: none;
}

button.submit-btn {
  padding: 15px 30px;
  background: linear-gradient(90deg, #2980b9, #1c5e88);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1.3rem;
  font-weight: bold;
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.3s ease;
  display: block;
  width: 100%;
  max-width: 300px;
  margin: 20px auto 0;
}

button.submit-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.user-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 20px;
  margin-bottom: 12px;
  background-color: #ffffff;
  border-radius: 8px;
  border: 1px solid #ccd5e1;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

.user-item:hover {
  background-color: #f0f4f8;
}

.user-item.selected {
  background-color: #1e88e5;
  color: white;
  border: none;
}

button[type="button"] {
  padding: 8px 16px;
  background-color: #1e88e5;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

button[type="button"]:hover {
  background-color: #1565c0;
  transform: scale(1.05);
}

button[type="button"].selected {
  background-color: #1565c0;
}

@media (max-width: 768px) {
  .create-project {
    width: 90%;
    padding: 20px;
  }

  button.submit-btn {
    max-width: 100%;
  }
}
</style>
