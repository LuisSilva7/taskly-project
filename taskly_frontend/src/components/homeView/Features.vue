<template>
  <div class="features">
    <h2>Site Features</h2>
    <ul>
      <li>Project and task management</li>
      <li>Workflow automation</li>
      <li>Detailed reports and analytics</li>
      <li>Facilitated team collaboration</li>
      <li>Real-time chat</li>
      <li>Share your most used files easily</li>
    </ul>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.features {
  text-align: center;
  max-width: 80%;
  padding: 0 20px;
  margin-left: auto;
  margin-right: auto;
}

.features h2 {
  font-size: 2rem;
  color: #094067;
  margin-bottom: 20px;
}

.features ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.features li {
  font-size: 1.2rem;
  color: #094067;
  display: flex;
  align-items: center;
  background-color: #f5faff;
  padding: 15px;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: calc(50% - 10px);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.features li:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.features li::before {
  content: "✔";
  margin-right: 12px;
  color: #1565c0;
  font-weight: bold;
  font-size: 1.4rem;
}
</style>
