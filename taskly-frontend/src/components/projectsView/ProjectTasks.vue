<template>
  <section v-if="project && tasks" class="project-tasks">
    <div class="task-header">
      <h2>Project Tasks</h2>
      <button @click="addNewTask" :disabled="isLoading" class="add-task-button">
        Add New Task
      </button>
    </div>
    <div v-if="tasks.length === 0" class="no-tasks">
      <p>No tasks assigned to this project.</p>
    </div>
    <div v-else class="task-list">
      <div v-for="task in tasks" :key="task.id" class="task-item">
        <h3>{{ task.title }}</h3>
        <p><strong>Description:</strong> {{ task.description }}</p>
        <p><strong>Status:</strong> {{ formatStatus(task.status) }}</p>
        <p>
          <strong>Task Members: </strong>
          <template v-if="resolvedTaskMembersNames[task.id]">
            {{ resolvedTaskMembersNames[task.id].join(", ") }}
          </template>
          <template v-else> Loading task members... </template>
        </p>
        <div v-if="task.taskComments.length > 0" class="task-comments">
          <h4>Comments</h4>
          <div
            v-for="comment in task.taskComments"
            :key="comment.id"
            class="comment-item"
          >
            <p class="comment-text">
              {{ comment.text }}
              <span class="comment-user">{{ comment.fullName }}</span>
            </p>
          </div>
        </div>
        <div class="add-comment">
          <input v-model="newComments[task.id]" placeholder="Add a comment" />
          <button
            @click="addComment(task.id)"
            :disabled="isLoading || !newComments[task.id]"
          >
            Add Comment
          </button>
        </div>
      </div>
    </div>
  </section>
  <div v-else>
    <p>Loading project tasks...</p>
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
      tasks: [],
      resolvedTaskMembersNames: {},
      newComments: {},
      isLoading: false,
    };
  },
  created() {
    this.fetchProjectDetails();
    this.fetchTasks();
  },
  methods: {
    async fetchProjectDetails() {
      try {
        const response = await api.get(`/projects/${this.projectId}`);
        this.project = response.data.data;
      } catch (error) {
        console.error("Error fetching project details:", error);
        alert("Failed to load project details.");
      }
    },

    async fetchTasks() {
      try {
        const response = await api.get(`/projects/${this.projectId}/tasks`);
        this.tasks = response.data.data.content;
        for (const task of this.tasks) {
          this.resolvedTaskMembersNames[task.id] = await this.taskMembersNames(
            task.taskMembersId
          );
        }
      } catch (error) {
        console.error("Error fetching tasks:", error);
        alert("Failed to load tasks.");
      }
    },

    addNewTask() {
      this.$router.push(`/createTask/${this.projectId}`);
    },

    async taskMembersNames(members) {
      const names = [];
      for (const id of members) {
        try {
          const response = await api.get(`/users/${id}`);
          names.push(
            `${response.data.data.firstname} ${response.data.data.lastname}`
          );
        } catch (error) {
          console.error(`Error fetching user name for ID ${id}:`, error);
          names.push("Unknown User");
        }
      }
      return names;
    },

    formatStatus(status) {
      return status === "in_progress"
        ? "In Progress"
        : status.replace("_", " ");
    },

    async addComment(taskId) {
      if (!this.newComments[taskId]?.trim()) return;
      try {
        this.isLoading = true;
        const comment = {
          text: this.newComments[taskId],
        };
        await api.post(`/tasks/${taskId}/comments`, comment);
        this.newComments[taskId] = "";
        this.fetchTasks();
      } catch (error) {
        console.error("Error adding comment:", error);
        alert("Failed to add comment.");
      } finally {
        this.isLoading = false;
      }
    },
  },
};
</script>

<style scoped>
.project-tasks {
  padding: 30px;
  border-radius: 12px;
}

.task-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
}

.task-header h2 {
  margin: 0;
  color: #2c3e50;
  font-size: 2rem;
  font-weight: bold;
}

.task-header .add-task-button {
  padding: 12px 25px;
  background: linear-gradient(90deg, #3498db, #2980b9);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1.1rem;
  font-weight: bold;
  transition: all 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.task-header .add-task-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.task-header .add-task-button:disabled {
  background-color: #b0bec5;
  cursor: not-allowed;
  box-shadow: none;
}

.task-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.task-item {
  padding: 15px;
  border-radius: 8px;
  border: 1px solid #dbe4ea;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  background-color: #f9f9f9;
}

.task-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.task-item h3 {
  margin: 0 0 15px 0;
  color: #455a64;
  font-size: 1.4rem;
  font-weight: bold;
}

.task-item p {
  font-size: 1.1rem;
  margin-bottom: 10px;
}

.task-item p strong {
  color: #1e88e5;
}

.add-comment {
  margin-top: 15px;
  display: flex;
  gap: 10px;
  align-items: center;
}

.add-comment input {
  padding: 12px;
  width: 100%;
  font-size: 1rem;
  border-radius: 8px;
  border: 1px solid #ccd5e1;
  transition: border-color 0.3s ease;
}

.add-comment input:focus {
  outline: none;
  border-color: #1e88e5;
}

.add-comment button {
  padding: 10px 20px;
  background: linear-gradient(90deg, #1e88e5, #1565c0);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 1rem;
}

.add-comment button:hover {
  background: linear-gradient(90deg, #1565c0, #1e88e5);
  transform: scale(1.05);
}

.add-comment button:disabled {
  background-color: #b0bec5;
  cursor: not-allowed;
}

.task-comments {
  margin-top: 20px;
}

.task-comments h4 {
  margin-bottom: 10px;
  color: #2c3e50;
  font-size: 1.2rem;
  font-weight: bold;
}

.comment-item {
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f7f9fc;
}

.comment-text {
  font-size: 1rem;
  margin: 0;
  color: #546e7a;
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.comment-user {
  font-size: 0.9rem;
  color: #78909c;
  margin-left: 10px;
  white-space: nowrap;
  text-align: right;
}

.comment-item p {
  margin: 0;
  font-size: 1rem;
}

.no-tasks p {
  font-size: 1.2rem;
  color: #78909c;
  text-align: center;
}
</style>
