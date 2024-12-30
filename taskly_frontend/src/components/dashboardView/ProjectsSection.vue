<template>
  <section class="hero">
    <div class="projects-container">
      <div class="header">
        <h2>📂 Recent Projects</h2>
      </div>
      <div class="projects-list">
        <ul>
          <li
            v-for="project in ongoingProjects"
            :key="project.id"
            class="project-item"
          >
            <div class="project-info">
              <div class="project-header">
                <span class="project-name">{{ project.name }}</span>
                <span class="project-priority" :class="project.priority">
                  {{ project.priority }}
                </span>
              </div>
              <p class="project-description">{{ project.description }}</p>
              <p class="project-dates">
                Start Date: {{ project.startDate }} | Expected End:
                {{ project.expectedEndDate }}
              </p>
              <p class="project-category">Category: {{ project.category }}</p>
            </div>

            <div class="progress-bar">
              <div
                class="progress"
                :style="{ width: project.progress + '%' }"
              ></div>
            </div>

            <div class="project-actions">
              <span class="progress-text"
                >{{ project.progress }}% completed</span
              >
              <button @click="viewProject(project.id)" class="view-btn">
                View Details
              </button>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </section>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      ongoingProjects: [],
    };
  },
  mounted() {
    this.fetchProjects();
  },
  methods: {
    async fetchProjects() {
      try {
        const token = localStorage.getItem("auth_token");

        if (!token) {
          throw new Error("No authentication token found");
        }

        const response = await axios.get(
          "/api/v1/projects?pageNumber=0&pageSize=2",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        this.ongoingProjects = response.data.data.content;
      } catch (error) {
        console.error("Error fetching projects:", error);
        alert("Failed to load projects.");
      }
    },
    viewProject(projectId) {
      this.$router.push({ name: "Project", params: { projectId } });
    },
  },
};
</script>

<style scoped>
.hero {
  padding: 30px;
  border-radius: 12px;
  margin-bottom: 30px;
}

.projects-container {
  position: relative;
}

.header {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 30px;
}

h2 {
  font-size: 2rem;
  color: #2c3e50;
  text-align: center;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px;
}

.project-item {
  background: #ffffff;
  border: 1px solid #dbe2e8;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.project-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.15);
}

.project-info {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

.project-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.project-name {
  font-size: 1.6rem;
  font-weight: 600;
  color: #2c3e50;
}

.project-priority {
  font-size: 1rem;
  font-weight: 600;
  padding: 5px 12px;
  border-radius: 6px;
  color: white;
  text-align: center;
}

.project-priority.Low {
  background-color: #06d6a0;
}

.project-priority.Medium {
  background-color: #ffd166;
}

.project-priority.High {
  background-color: #ef476f;
}

.project-description {
  font-size: 1rem;
  color: #7f8c8d;
  margin-top: 10px;
}

.project-dates {
  font-size: 0.9rem;
  color: #34495e;
  margin-top: 10px;
}

.project-category {
  font-size: 1rem;
  color: #2c3e50;
  font-weight: 500;
  margin-top: 10px;
}

.progress-bar {
  margin-top: 20px;
  height: 10px;
  background: #ecf0f1;
  border-radius: 5px;
  position: relative;
}

.progress {
  height: 100%;
  background: #3498db;
  border-radius: 5px;
  transition: width 0.3s ease;
}

.project-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 15px;
}

.progress-text {
  font-size: 1rem;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 10px;
}

.view-btn {
  padding: 12px 25px;
  background-color: #3498db;
  color: #ffffff;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.view-btn:hover {
  background-color: #2c3e50;
  transform: scale(1.05);
}
</style>
