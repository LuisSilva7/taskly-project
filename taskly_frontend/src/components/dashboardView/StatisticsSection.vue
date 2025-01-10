<template>
  <section class="reports">
    <h2>📊 Statistics</h2>
    <div class="stats">
      <div class="stat" v-if="stats.projectsParticipated !== null">
        <i class="icon fas fa-clipboard-check"></i>
        <h3>Completed Projects</h3>
        <p>{{ stats.completedProjects }} / {{ stats.projectsParticipated }}</p>
        <div class="progress-bar">
          <div
            class="progress completed"
            :style="{ width: progressCompletedProjects + '%' }"
          ></div>
        </div>
        <small>{{ progressCompletedProjects }}% completed</small>
      </div>
      <div class="stat" v-if="stats.completedTasks !== null">
        <i class="icon fas fa-check-circle"></i>
        <h3>Completed Tasks</h3>
        <p>{{ stats.completedTasks }} / {{ stats.totalTasks }}</p>
        <div class="progress-bar">
          <div
            class="progress completed"
            :style="{ width: progressCompleted + '%' }"
          ></div>
        </div>
        <small>{{ progressCompleted }}% completed</small>
      </div>
      <div class="stat" v-if="stats.completedTasks > 0">
        <i class="icon fas fa-clock"></i>
        <h3>Average Task Completion Time</h3>
        <p>{{ averageCompletionTime }} days</p>
      </div>
      <div class="stat" v-if="stats.completedTasks > 0">
        <i class="icon fas fa-calendar-check"></i>
        <h3>Schedule Compliance Rate p/ completedTasks</h3>
        <p>{{ scheduleComplianceRate }}%</p>
      </div>
      <div class="stat" v-if="stats.messagesSent !== undefined">
        <i class="icon fas fa-comment-dots"></i>
        <h3>Messages Sent to Chat</h3>
        <p>{{ stats.messagesSent }} / 20</p>
        <div class="progress-bar">
          <div
            class="progress messages"
            :style="{ width: progressMessages + '%' }"
          ></div>
        </div>
        <small>{{ progressMessages }}% messages sent</small>
      </div>
    </div>
  </section>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      stats: {
        completedTasks: null,
        totalTasks: null,
        completedProjects: null,
        projectsParticipated: null,
        messagesSent: 15,
      },
    };
  },
  computed: {
    progressCompleted() {
      const totalTasks = this.stats.totalTasks;
      return totalTasks > 0
        ? Math.round((this.stats.completedTasks / totalTasks) * 100)
        : 0;
    },
    progressCompletedProjects() {
      const totalProjects = this.stats.projectsParticipated;
      return totalProjects > 0
        ? Math.round((this.stats.completedProjects / totalProjects) * 100)
        : 0;
    },
    progressMessages() {
      return Math.round((this.stats.messagesSent / 20) * 100);
    },
    averageCompletionTime() {
      if (this.stats.completedTasks > 0) {
        return (Math.random() * (10 - 1) + 1).toFixed(2);
      }
      return 0;
    },
    scheduleComplianceRate() {
      if (this.stats.completedTasks > 0) {
        return Math.round(Math.random() * 100);
      }
      return 0;
    },
  },
  mounted() {
    this.fetchStatistics();
  },
  methods: {
    async fetchStatistics() {
      try {
        const token = localStorage.getItem("auth_token");
        const config = {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        };
        const projectsResponse = await axios.get(
          "/api/v1/statistics/completedProjects",
          config
        );
        this.stats.completedProjects =
          projectsResponse.data.data.completedProjects;
        this.stats.projectsParticipated =
          projectsResponse.data.data.totalProjects;
        const tasksResponse = await axios.get(
          "/api/v1/statistics/completedTasks",
          config
        );
        this.stats.completedTasks = tasksResponse.data.data.completedTasks;
        this.stats.totalTasks = tasksResponse.data.data.totalTasks;
      } catch (error) {
        console.error("Error fetching statistics data:", error);
      }
    },
  },
};
</script>

<style scoped>
.reports {
  padding: 30px;
  border-radius: 12px;
  margin-bottom: 20px;
}

.reports h2 {
  text-align: center;
  font-size: 2rem;
  color: #094067;
  margin-bottom: 30px;
}

.stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 20px;
  justify-items: center;
}

.stat {
  background: #ffffff;
  border: 1px solid #d1e3f3;
  border-radius: 12px;
  padding: 20px;
  width: 100%;
  max-width: 320px;
  text-align: center;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.stat:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
}

.stat h3 {
  font-size: 1.4rem;
  color: #094067;
  margin-bottom: 10px;
}

.stat p {
  font-size: 2rem;
  font-weight: bold;
  color: #1565c0;
}

.icon {
  font-size: 3rem;
  color: #1565c0;
  margin-bottom: 10px;
}

.progress-bar {
  width: 100%;
  height: 8px;
  background-color: #eef3f8;
  border-radius: 4px;
  margin: 10px 0;
  position: relative;
}

.progress {
  height: 100%;
  border-radius: 4px;
  transition: width 0.3s ease;
}

.progress.completed {
  background: linear-gradient(90deg, #3da9fc, #1565c0);
}

.progress.messages {
  background: linear-gradient(90deg, #55c57a, #2aa655);
}

small {
  font-size: 0.9rem;
  color: #555;
}
</style>
