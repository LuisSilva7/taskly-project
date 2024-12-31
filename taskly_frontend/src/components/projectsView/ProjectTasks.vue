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
      this.$router.push(`/addTask/${this.projectId}`);
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
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 15px;
}

.task-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.task-header h2 {
  margin-top: 0;
  color: #2c3e50;
  font-size: 1.8rem;
}

.task-header .add-task-button {
  padding: 10px 20px;
  background-color: #094067;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.task-header .add-task-button:hover {
  transform: scale(1.05);
}

.task-header .add-task-button:disabled {
  background-color: #95a5a6;
  cursor: not-allowed;
}

.task-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.task-item {
  background-color: white;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.task-item h3 {
  margin-top: 0;
  color: #3498db;
}

.task-item p {
  color: #34495e;
  font-size: 1.1rem;
}

.add-comment {
  margin-top: 20px;
  display: flex;
  gap: 10px;
  align-items: center;
}

.add-comment input {
  padding: 10px;
  width: 100%;
  font-size: 1rem;
  border-radius: 6px;
  border: 1px solid #ccc;
}

.add-comment button {
  padding: 10px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.add-comment button:disabled {
  background-color: #95a5a6;
  cursor: not-allowed;
}

.task-comments {
  margin-top: 20px;
}

.task-comments h4 {
  margin-bottom: 5px;
}

.comment-item {
  background-color: #ecf0f1;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 6px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.comment-text {
  font-size: 1rem;
  margin: 0;
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.comment-user {
  font-size: 0.9rem;
  color: #7f8c8d;
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
  color: #7f8c8d;
}
</style>
