<template>
  <section class="tasks">
    <h2>📝 Pending Tasks</h2>
    <ul>
      <li v-for="task in tasks" :key="task.id" class="task-item">
        <div class="task-info">
          <span class="task-name">{{ task.title }}</span>
          <p class="task-description">{{ task.description }}</p>
          <span class="task-project">Project: {{ task.projectName }}</span>
        </div>
        <div class="task-details">
          <span class="deadline">Deadline: {{ task.deadline }}</span>
          <button @click="completeTask(task.id)" class="complete-btn">
            ✔️ Complete
          </button>
        </div>
      </li>
    </ul>
  </section>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      tasks: [],
    };
  },
  mounted() {
    this.fetchTasks();
  },
  methods: {
    async fetchTasks() {
      try {
        const token = localStorage.getItem("auth_token");
        const response = await axios.get("/api/v1/tasks/user", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        const inProgressTasks = response.data.data.content.filter(
          (task) => task.status === "in_progress"
        );
        const tasksWithProjectName = await Promise.all(
          inProgressTasks.map(async (task) => {
            try {
              const projectResponse = await axios.get(
                `/api/v1/projects/${task.projectId}`,
                {
                  headers: {
                    Authorization: `Bearer ${token}`,
                  },
                }
              );
              task.projectName = projectResponse.data.data.name;
              return task;
            } catch (error) {
              console.error("Error fetching project for task:", error);
              return task;
            }
          })
        );
        this.tasks = tasksWithProjectName;
      } catch (error) {
        console.error("Error fetching tasks:", error);
        alert("Failed to fetch tasks");
      }
    },
    async completeTask(id) {
      try {
        const token = localStorage.getItem("auth_token");
        const response = await axios.put(
          `/api/v1/tasks/${id}/complete`,
          {},
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        this.fetchTasks();
        alert("Task completed successfully!");
      } catch (error) {
        console.error("Error completing task:", error);
        alert("Failed to complete the task.");
      }
    },
  },
};
</script>

<style scoped>
.tasks {
  padding: 20px;
  border-radius: 12px;
  margin-bottom: 20px;
}

.tasks h2 {
  font-size: 1.8rem;
  color: #094067;
  margin-bottom: 20px;
  text-align: center;
}

.tasks ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.task-item {
  background: #ffffff;
  border: 1px solid #d1e3f3;
  border-radius: 10px;
  padding: 15px;
  margin-bottom: 15px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  align-items: center;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.task-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.15);
}

.task-info {
  flex: 2;
  display: flex;
  flex-direction: column;
}

.task-name {
  font-size: 1.2rem;
  font-weight: bold;
  color: #094067;
}

.task-description {
  font-size: 0.9rem;
  color: #555;
  margin-top: 5px;
}

.task-project {
  font-size: 0.9rem;
  font-weight: 500;
  color: #2c3e50;
  margin-top: 5px;
}

.task-priority {
  font-size: 0.9rem;
  font-weight: bold;
  margin-top: 5px;
  padding: 5px 10px;
  border-radius: 5px;
  text-align: center;
}

.task-priority.high {
  background-color: #ef476f;
  color: white;
}

.task-priority.medium {
  background-color: #ffd166;
  color: white;
}

.task-priority.low {
  background-color: #06d6a0;
  color: white;
}

.task-details {
  display: flex;
  align-items: center;
  gap: 15px;
}

.deadline {
  font-size: 0.9rem;
  font-weight: 500;
  color: #555;
}

.complete-btn {
  padding: 8px 12px;
  background-color: #3da9fc;
  color: #ffffff;
  border: none;
  border-radius: 5px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.complete-btn:hover {
  background-color: #094067;
  transform: scale(1.05);
}
</style>
