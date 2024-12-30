<template>
  <section class="reports">
    <h2>📊 Statistics</h2>
    <div class="stats">
      <div class="stat">
        <i class="icon fas fa-folder"></i>
        <h3>Total Projects</h3>
        <p>{{ stats.totalProjects }}</p>
      </div>

      <div class="stat">
        <i class="icon fas fa-check-circle"></i>
        <h3>Completed Tasks</h3>
        <p>{{ stats.completedTasks }}</p>
        <div class="progress-bar">
          <div
            class="progress completed"
            :style="{ width: progressCompleted + '%' }"
          ></div>
        </div>
        <small>{{ progressCompleted }}% completed</small>
      </div>

      <div class="stat">
        <i class="icon fas fa-tasks"></i>
        <h3>Pending Tasks</h3>
        <p>{{ stats.pendingTasks }}</p>
        <div class="progress-bar">
          <div
            class="progress pending"
            :style="{ width: progressPending + '%' }"
          ></div>
        </div>
        <small>{{ progressPending }}% pending</small>
      </div>

      <div class="stat">
        <i class="icon fas fa-exclamation-circle"></i>
        <h3>Overdue Tasks</h3>
        <p>{{ stats.overdueTasks }}</p>
        <div class="progress-bar">
          <div
            class="progress overdue"
            :style="{ width: progressOverdue + '%' }"
          ></div>
        </div>
        <small>{{ progressOverdue }}% overdue</small>
      </div>

      <div class="stat">
        <i class="icon fas fa-check-square"></i>
        <h3>Completed Projects</h3>
        <p>{{ stats.completedProjects }}</p>
      </div>

      <div class="stat">
        <i class="icon fas fa-clock"></i>
        <h3>Average Task Duration</h3>
        <p>{{ stats.averageTaskDuration }} days</p>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      stats: {
        totalProjects: 50,
        completedTasks: 120,
        pendingTasks: 30,
        overdueTasks: 10,
        completedProjects: 40,
        averageTaskDuration: 5,
      },
    };
  },
  computed: {
    progressCompleted() {
      const total = this.stats.completedTasks + this.stats.pendingTasks;
      return total > 0
        ? Math.round((this.stats.completedTasks / total) * 100)
        : 0;
    },
    progressPending() {
      const total = this.stats.completedTasks + this.stats.pendingTasks;
      return total > 0
        ? Math.round((this.stats.pendingTasks / total) * 100)
        : 0;
    },
    progressOverdue() {
      const total =
        this.stats.completedTasks +
        this.stats.pendingTasks +
        this.stats.overdueTasks;
      return total > 0
        ? Math.round((this.stats.overdueTasks / total) * 100)
        : 0;
    },
  },
};
</script>

<style scoped>
.reports {
  padding: 20px;
  border-radius: 12px;
  margin-bottom: 20px;
}

.reports h2 {
  text-align: center;
  font-size: 1.8rem;
  color: #094067;
  margin-bottom: 20px;
}

.stats {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.stat {
  flex: 1;
  min-width: 250px;
  text-align: center;
  background: #ffffff;
  border: 1px solid #d1e3f3;
  border-radius: 12px;
  padding: 20px;
  position: relative;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.stat:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
}

.stat h3 {
  font-size: 1.2rem;
  color: #094067;
  margin-bottom: 10px;
}

.stat p {
  font-size: 2rem;
  font-weight: bold;
  color: #3da9fc;
}

.icon {
  font-size: 2.5rem;
  color: #3da9fc;
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
  background-color: #3da9fc;
}

.progress.pending {
  background-color: #ffb703;
}

.progress.overdue {
  background-color: #e94e77;
}

small {
  font-size: 0.9rem;
  color: #777;
}
</style>
