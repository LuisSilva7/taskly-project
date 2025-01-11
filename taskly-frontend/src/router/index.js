import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Register from "../views/Register.vue";
import Login from "../views/Login.vue";
import Dashboard from "../views/Dashboard.vue";
import Projects from "../views/Projects.vue";
import CreateProject from "../views/CreateProject.vue";
import Project from "../views/Project.vue";
import CreateTask from "../views/CreateTask.vue";
import Chat from "../views/Chat.vue";
import Profile from "../views/Profile.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/dashboard",
    name: "Dashboard",
    component: Dashboard,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/projects",
    name: "Projects",
    component: Projects,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/createProject",
    name: "CreateProject",
    component: CreateProject,
    props: true,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/project/:projectId",
    name: "Project",
    component: Project,
    props: true,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/createTask/:projectId",
    name: "CreateTask",
    component: CreateTask,
    props: true,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/chat",
    name: "Chat",
    component: Chat,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
    meta: {
      requiresAuth: true,
    },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem("auth_token");

  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (!isAuthenticated) {
      next({ path: "/login" });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
