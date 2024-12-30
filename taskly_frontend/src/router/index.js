import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Register from "../views/Register.vue";
import Login from "../views/Login.vue";
import Dashboard from "../views/Dashboard.vue";
import Projects from "../views/Projects.vue";
import CreateProject from "../views/CreateProject.vue";
import Tasks from "../views/Tasks.vue";
import Reports from "../views/Reports.vue";
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
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/tasks",
    name: "Tasks",
    component: Tasks,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/reports",
    name: "Reports",
    component: Reports,
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
