<template>
  <section class="create-task">
    <h2>Create a New Task</h2>
    <form @submit.prevent="submitTask">
      <div class="form-group">
        <label for="title">Task Title</label>
        <input type="text" id="title" v-model="task.title" required />
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <textarea
          id="description"
          v-model="task.description"
          required
        ></textarea>
      </div>
      <div class="form-group">
        <label for="deadline">Deadline</label>
        <input type="date" id="deadline" v-model="task.deadline" required />
      </div>
      <div class="form-group">
        <label for="assignee">Assign Members</label>
        <div
          v-for="user in projectMembers"
          :key="user.id"
          class="user-item"
          :class="{ selected: task.assignees.includes(user.id) }"
        >
          <span>
            {{ user.firstname }} {{ user.lastname }} - {{ user.jobRole }}
          </span>
          <button type="button" @click="addAssignee(user.id)">
            {{ task.assignees.includes(user.id) ? "Selected" : "+" }}
          </button>
        </div>
      </div>
      <button type="submit" class="submit-btn">Create Task</button>
    </form>
  </section>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      task: {
        title: "",
        description: "",
        deadline: "",
        status: "in_progress",
        assignees: [],
      },
      projectMembersIds: [],
      projectMembers: [],
    };
  },
  created() {
    this.fetchProjectDetails();
  },
  methods: {
    async fetchProjectDetails() {
      try {
        const token = localStorage.getItem("auth_token");
        const projectId = this.$route.params.projectId;
        const response = await axios.get(`/api/v1/projects/${projectId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        const project = response.data.data;
        this.projectMembersIds = project.projectMembersId;
        this.fetchProjectMembers();
      } catch (error) {
        console.error("Error fetching project details:", error);
      }
    },
    async fetchProjectMembers() {
      try {
        const token = localStorage.getItem("auth_token");
        const memberPromises = this.projectMembersIds.map(async (memberId) => {
          const response = await axios.get(`/api/v1/users/${memberId}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          return response.data.data;
        });
        this.projectMembers = await Promise.all(memberPromises);
      } catch (error) {
        console.error("Error fetching project members:", error);
      }
    },
    addAssignee(userId) {
      const index = this.task.assignees.indexOf(userId);
      if (index > -1) {
        this.task.assignees.splice(index, 1);
      } else {
        this.task.assignees.push(userId);
      }
    },
    async submitTask() {
      try {
        const taskData = {
          title: this.task.title,
          description: this.task.description,
          deadline: this.task.deadline,
          status: this.task.status,
          taskMembersId: this.task.assignees,
        };
        const token = localStorage.getItem("auth_token");
        await axios.post(
          `/api/v1/projects/${this.$route.params.projectId}/tasks`,
          taskData,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        alert("Task created successfully!");
        this.$router.push(`/project/${this.$route.params.projectId}`);
      } catch (error) {
        console.error("Error creating task:", error);
        alert("Error creating task.");
      }
    },
  },
};
</script>

<style scoped>
.create-task {
  padding: 40px;
  border-radius: 12px;
  background-color: #f5faff; /* Fundo azul claro */
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
  .create-task {
    width: 90%;
    padding: 20px;
  }

  button.submit-btn {
    max-width: 100%;
  }
}
</style>
