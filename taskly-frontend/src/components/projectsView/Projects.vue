<template>
  <div class="projects-page">
    <section class="filters-container">
      <div class="filters">
        <input
          type="text"
          placeholder="Search by name..."
          v-model="filters.name"
          @input="applyFilters"
          class="filter-input"
        />
        <select
          v-model="filters.status"
          @change="applyFilters"
          class="filter-select"
        >
          <option value="">All Status</option>
          <option value="in_progress">In Progress</option>
          <option value="completed">Completed</option>
        </select>
        <select
          v-model="filters.category"
          @change="applyFilters"
          class="filter-select"
        >
          <option value="">All Categories</option>
          <option value="Design">Design</option>
          <option value="Development">Development</option>
          <option value="Education">Education</option>
          <option value="Finance">Finance</option>
          <option value="Human Resources">Human Resources</option>
          <option value="Marketing">Marketing</option>
          <option value="Operations">Operations</option>
          <option value="Research">Research</option>
          <option value="Sales">Sales</option>
          <option value="Technology">Technology</option>
        </select>
        <select
          v-model="filters.priority"
          @change="applyFilters"
          class="filter-select"
        >
          <option value="">All Priorities</option>
          <option value="Low">Low</option>
          <option value="Medium">Medium</option>
          <option value="High">High</option>
        </select>
      </div>
      <button class="create-btn" @click="createNewProject">
        Create New Project
      </button>
    </section>
    <section class="projects-container">
      <div class="projects-list">
        <ul>
          <li
            v-for="project in filteredProjects"
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
                Start Date: {{ formatDate(project.startDate) }} | Expected End:
                {{ formatDate(project.expectedEndDate) }}
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
      <div class="pagination">
        <button
          v-if="!pageInfo.first"
          @click="fetchProjects(pageInfo.number - 1)"
        >
          Previous
        </button>

        <span>Page {{ pageInfo.number + 1 }} of {{ pageInfo.totalPages }}</span>

        <button
          v-if="!pageInfo.last"
          @click="fetchProjects(pageInfo.number + 1)"
        >
          Next
        </button>
      </div>
    </section>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      projects: [],
      filteredProjects: [],
      filters: {
        name: "",
        status: "",
        category: "",
        priority: "",
      },
      loading: false,
      pageInfo: {
        number: 0,
        size: 4,
        totalPages: 0,
        totalElements: 0,
        first: true,
        last: false,
      },
    };
  },
  methods: {
    async fetchProjects(pageNumber = 0) {
      this.loading = true;
      try {
        const token = localStorage.getItem("auth_token");
        const response = await axios.get(
          `/api/v1/projects?pageNumber=${pageNumber}&pageSize=${this.pageInfo.size}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const data = response.data.data;
        this.projects = data.content;
        this.pageInfo = {
          number: data.number,
          size: data.size,
          totalPages: data.totalPages,
          totalElements: data.totalElements,
          first: data.first,
          last: data.last,
        };
        this.applyFilters();
      } catch (error) {
        console.error("Error fetching projects:", error);
        alert("Failed to load projects.");
      } finally {
        this.loading = false;
      }
    },
    applyFilters() {
      this.filteredProjects = this.projects.filter((project) => {
        const matchesName =
          this.filters.name === "" ||
          project.name.toLowerCase().includes(this.filters.name.toLowerCase());
        const matchesStatus =
          this.filters.status === "" || project.status === this.filters.status;
        const matchesCategory =
          this.filters.category === "" ||
          project.category === this.filters.category;
        const matchesPriority =
          this.filters.priority === "" ||
          project.priority === this.filters.priority;

        return (
          matchesName && matchesStatus && matchesCategory && matchesPriority
        );
      });
    },
    formatDate(date) {
      return new Date(date).toLocaleDateString();
    },
    viewProject(projectId) {
      this.$router.push({ name: "Project", params: { projectId } });
    },
    createNewProject() {
      this.$router.push("/createProject");
    },
  },
  mounted() {
    this.fetchProjects();
  },
};
</script>

<style scoped>
.projects-page {
  padding: 50px 30px;
  background-color: #f5faff;
  border-radius: 12px;
}

.page-title {
  font-size: 2.5rem;
  text-align: center;
  color: #094067;
  margin-bottom: 30px;
}

.filters-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.filters {
  display: flex;
  gap: 10px;
}

.filter-input,
.filter-select {
  padding: 10px 15px;
  font-size: 1rem;
  border: 1px solid #d1e3f3;
  border-radius: 6px;
  background-color: #ffffff;
}

.create-btn {
  padding: 12px 25px;
  background: linear-gradient(90deg, #094067, #1565c0);
  color: #ffffff;
  border: none;
  border-radius: 6px;
  font-size: 1.2rem;
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.3s ease;
}

.create-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.projects-container {
  margin-top: 30px;
}

.projects-list ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px;
}

.project-item {
  background: #ffffff;
  border: 1px solid #d1e3f3;
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

.project-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.project-name {
  font-size: 1.6rem;
  font-weight: 600;
  color: #094067;
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
  background: linear-gradient(90deg, #06d6a0, #02b882);
}

.project-priority.Medium {
  background: linear-gradient(90deg, #ffd166, #ffa500);
}

.project-priority.High {
  background: linear-gradient(90deg, #ef476f, #d32f2f);
}

.project-description {
  font-size: 1rem;
  color: #5d6d7e;
  margin-top: 10px;
}

.project-dates {
  font-size: 0.9rem;
  color: #34495e;
  margin-top: 10px;
}

.project-category {
  font-size: 1rem;
  color: #094067;
  font-weight: 500;
  margin-top: 10px;
}

.progress-bar {
  margin-top: 20px;
  height: 10px;
  background-color: #eef3f8;
  border-radius: 5px;
  position: relative;
}

.progress {
  height: 100%;
  background: linear-gradient(90deg, #3da9fc, #1565c0);
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
  color: #094067;
  margin-bottom: 10px;
}

.view-btn {
  padding: 12px 25px;
  background: linear-gradient(90deg, #094067, #1565c0);
  color: #ffffff;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease,
    box-shadow 0.3s ease;
}

.view-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  margin-top: 30px;
}

.pagination button {
  padding: 10px 20px;
  border: none;
  border-radius: 6px;
  background: linear-gradient(90deg, #3da9fc, #1565c0);
  color: #fff;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.pagination span {
  font-size: 1.2rem;
  color: #094067;
}
</style>
