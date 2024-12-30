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
        console.error("Erro ao criar o projeto:", error);
        alert("Falha ao criar o projeto.");
      }
    },
  },
};
</script>

<style scoped>
.create-project {
  padding: 30px;
  border-radius: 12px;
  margin-bottom: 30px;
  background-color: #f9f9f9;
  width: 60%;
  margin: 0 auto;
}

h2 {
  font-size: 2rem;
  color: #333;
  text-align: center;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-size: 1rem;
  color: #333;
  font-weight: bold;
}

input,
textarea,
select {
  width: 100%;
  padding: 12px;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  margin-top: 5px;
  background-color: #fff;
}

textarea {
  height: 100px;
}

button.submit-btn {
  padding: 12px 25px;
  background-color: #2980b9;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 1.2rem;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  margin-top: 20px;
}

button.submit-btn:hover {
  background-color: #1c5e88;
  transform: scale(1.05);
}

.user-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  margin-bottom: 10px;
  background-color: #f5f5f5;
  border-radius: 6px;
  transition: background-color 0.3s ease;
}

.user-item.selected {
  background-color: #2980b9;
  color: white;
}

button[type="button"] {
  margin-left: 10px;
  padding: 6px 12px;
  background-color: #2980b9;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

button[type="button"]:hover {
  background-color: #1c5e88;
}

button[type="button"].selected {
  background-color: #1c5e88;
}
</style>
