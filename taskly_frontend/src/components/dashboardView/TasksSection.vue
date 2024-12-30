<template>
  <section class="tasks">
    <h2>📝 Pending Tasks</h2>
    <ul>
      <li v-for="task in tasks" :key="task.name" class="task-item">
        <div class="task-info">
          <span class="task-name">{{ task.name }}</span>
          <p class="task-description">{{ task.description }}</p>
          <span class="task-project">Project: {{ task.project }}</span>
        </div>
        <div class="task-details">
          <span class="deadline">Deadline: {{ task.deadline }}</span>
          <button @click="completeTask(task)" class="complete-btn">
            ✔️ Complete
          </button>
        </div>
      </li>
    </ul>
  </section>
</template>

<script>
export default {
  data() {
    return {
      tasks: [
        {
          name: "Task Alpha",
          description: "Complete the user interface design for the new app.",
          deadline: "2024-02-15",
          project: "Project Alpha",
          priority: "high",
        },
        {
          name: "Task Beta",
          description: "Test the newly integrated features for performance.",
          deadline: "2024-03-01",
          project: "Project Beta",
          priority: "medium",
        },
        {
          name: "Task Gamma",
          description: "Prepare documentation for the final report.",
          deadline: "2024-01-20",
          project: "Project Gamma",
          priority: "low",
        },
        {
          name: "Task Delta",
          description: "Conduct a security audit of the backend services.",
          deadline: "2024-02-10",
          project: "Project Delta",
          priority: "high",
        },
      ],
    };
  },
  methods: {
    completeTask(task) {
      alert(`Task completed: ${task.name}`);
      // Implement logic for marking the task as completed
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
