<template>
  <section class="project-info" v-if="project">
    <div class="project-header">
      <h2>{{ project.name }}</h2>
      <div class="project-actions">
        <button @click="editProject" :disabled="isLoading">Edit Project</button>
        <button
          @click="completeProject"
          :disabled="isLoading || project.progress === 100"
        >
          Complete Project
        </button>
        <button @click="deleteProject" :disabled="isLoading" class="delete-btn">
          Delete Project
        </button>
      </div>
    </div>
    <div class="project-details">
      <p><strong>Description:</strong> {{ project.description }}</p>
      <p><strong>Start Date:</strong> {{ project.startDate }}</p>
      <p><strong>Expected End Date:</strong> {{ project.expectedEndDate }}</p>
      <p v-if="project.endDate">
        <strong>End Date:</strong> {{ project.endDate }}
      </p>
      <p><strong>Progress:</strong> {{ project.progress }}%</p>
      <p><strong>Status:</strong> {{ formattedStatus }}</p>
      <p><strong>Category:</strong> {{ project.category }}</p>
      <p><strong>Priority:</strong> {{ project.priority }}</p>
      <p><strong>Project Manager:</strong> {{ projectManagerName }}</p>
      <p>
        <strong>Project Members:</strong>
        {{ filteredProjectMembersNames.join(", ") }}
      </p>
    </div>
  </section>
  <div v-else>
    <p>Loading project details...</p>
  </div>
</template>

<script>
import axios from "axios";

const api = axios.create({
  baseURL: "/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("auth_token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default {
  props: {
    projectId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      project: null,
      projectManagerName: "",
      projectMembersNames: [],
      isLoading: true,
    };
  },
  computed: {
    formattedStatus() {
      return this.project.status === "in_progress"
        ? "In Progress"
        : "Completed";
    },
    filteredProjectMembersNames() {
      return this.projectMembersNames.filter(
        (name) => name !== this.projectManagerName
      );
    },
  },
  created() {
    this.fetchProjectDetails();
  },
  methods: {
    async fetchProjectDetails() {
      try {
        const response = await api.get(`/projects/${this.projectId}`);
        const project = response.data.data;
        this.project = project;
        this.fetchProjectManager(project.projectManagerId);
        this.fetchProjectMembers(project.projectMembersId);
      } catch (error) {
        console.error("Error fetching project details:", error);
        alert("Failed to load project details.");
      } finally {
        this.isLoading = false;
      }
    },
    async fetchProjectManager(managerId) {
      try {
        const response = await api.get(`/users/${managerId}`);
        this.projectManagerName = `${response.data.data.firstname} ${response.data.data.lastname}`;
      } catch (error) {
        console.error("Error fetching project manager:", error);
        this.projectManagerName = "Manager Not Found";
      }
    },
    async fetchProjectMembers(membersIds) {
      try {
        const membersPromises = membersIds.map((id) => api.get(`/users/${id}`));
        const membersResponses = await Promise.all(membersPromises);
        this.projectMembersNames = membersResponses.map(
          (response) =>
            `${response.data.data.firstname} ${response.data.data.lastname}`
        );
      } catch (error) {
        console.error("Error fetching project members:", error);
        this.projectMembersNames = ["Members not found"];
      }
    },
    editProject() {
      this.$router.push(`/editProject/${this.projectId}`);
    },
    async deleteProject() {
      if (confirm("Are you sure you want to delete this project?")) {
        try {
          await api.delete(`/projects/${this.projectId}`);
          this.$router.push("/projects");
          alert("Project deleted successfully!");
        } catch (error) {
          console.error("Error deleting project:", error);
          alert("Failed to delete project.");
        }
      }
    },
    async completeProject() {
      try {
        await api.put(`/projects/${this.projectId}/complete`);
        this.fetchProjectDetails();
        alert("Project completed successfully!");
      } catch (error) {
        console.error("Error completing project:", error);
        alert("Failed to complete project.");
      }
    },
  },
};
</script>

<style scoped>
.project-info {
  padding: 40px 20px;
}

.project-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.project-info h2 {
  margin-top: 0;
  color: #2c3e50;
  font-size: 2rem;
  font-weight: bold;
  flex-grow: 1;
  text-transform: capitalize;
}

.project-actions {
  display: flex;
  gap: 15px;
}

.project-details p {
  font-size: 1.2rem;
  margin-bottom: 12px;
  color: #5d6d7e;
}

.project-details p strong {
  color: #1e88e5;
}

.project-actions button {
  padding: 10px 20px;
  background: linear-gradient(90deg, #6c5ce7, #4e44e2);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 1rem;
  font-weight: 600;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.project-actions button:hover {
  background: linear-gradient(90deg, #4e44e2, #6c5ce7);
  transform: translateY(-2px);
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.15);
}

.project-actions button:disabled {
  background-color: #bdc3c7;
  cursor: not-allowed;
  box-shadow: none;
}

.project-actions button:disabled:hover {
  transform: none;
}

.project-actions .delete-btn {
  background: linear-gradient(90deg, #ff6b6b, #e74c3c);
}

.project-actions .delete-btn:hover {
  background: linear-gradient(90deg, #e74c3c, #ff6b6b);
}

.project-details {
  margin-top: 20px;
  padding: 20px;
  background: #ffffff;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.project-details p:not(:last-child) {
  border-bottom: 1px solid #eaeaea;
  padding-bottom: 8px;
  margin-bottom: 10px;
}
</style>
