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
