<template>
  <nav>
    <div class="logo">
      <router-link to="/" class="logo-link">
        <img src="../../assets/images/logoT.png" alt="Logo" />
        <span class="logo-text">askly</span>
      </router-link>
    </div>
    <div v-if="isAuthenticated">
      <div class="hamburger" @click="toggleMobileMenu">
        <div></div>
        <div></div>
        <div></div>
      </div>
      <ul :class="['menu', { 'menu-open': isMobileMenuOpen }]">
        <li>
          <router-link
            to="/dashboard"
            class="nav-link"
            active-class="active-link"
          >
            Dashboard
          </router-link>
        </li>
        <li>
          <router-link
            to="/projects"
            class="nav-link"
            active-class="active-link"
          >
            Projects
          </router-link>
        </li>
        <li>
          <router-link to="/tasks" class="nav-link" active-class="active-link">
            Tasks
          </router-link>
        </li>
        <li>
          <router-link to="/chat" class="nav-link" active-class="active-link">
            Chat
          </router-link>
        </li>
        <li class="profile" @click="toggleProfileMenu">
          <img
            class="profile-photo"
            :src="
              photoUrl || 'http://localhost:8888/uploads/users/user-default.png'
            "
            alt="Profile"
          />
          <span>{{ username }}</span>
          <i class="arrow-down"></i>
          <div v-if="isProfileMenuOpen" class="profile-menu">
            <ul>
              <li>
                <router-link to="/profile" class="nav-link">
                  Profile
                </router-link>
              </li>
              <li @click="logout">Logout</li>
            </ul>
          </div>
        </li>
      </ul>
    </div>
    <div v-else>
      <ul class="menu">
        <li>
          <router-link to="/register" class="nav-link">
            Get Started
          </router-link>
        </li>
        <li>
          <router-link to="/login" class="btn-login"> Login </router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      isProfileMenuOpen: false,
      isMobileMenuOpen: false,
      isAuthenticated: false,
      username: "",
      photoUrl: "",
    };
  },
  created() {
    this.checkAuthentication();
  },
  methods: {
    checkAuthentication() {
      const token = localStorage.getItem("auth_token");
      const tokenExpiration = localStorage.getItem("token_expiration");

      if (token && tokenExpiration && Date.now() < tokenExpiration) {
        this.isAuthenticated = true;
        this.username = localStorage.getItem("username");
        this.photoUrl = localStorage.getItem("photoUrl");
      } else {
        this.isAuthenticated = false;
      }
    },
    toggleProfileMenu() {
      this.isProfileMenuOpen = !this.isProfileMenuOpen;
    },

    toggleMobileMenu() {
      this.isMobileMenuOpen = !this.isMobileMenuOpen;
    },
    logout() {
      localStorage.removeItem("auth_token");
      localStorage.removeItem("token_expiration");
      localStorage.removeItem("username");
      localStorage.removeItem("photoUrl");

      this.$router.push("/");

      this.isAuthenticated = false;
    },
  },
};
</script>

<style scoped>
nav {
  background-color: #ffffff;
  color: rgb(0, 0, 0);
  padding: 10px 200px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-height: 60px;
}

.logo {
  display: flex;
  align-items: center;
}

.logo-link {
  display: flex;
  align-items: center;
  text-decoration: none;
  color: rgb(0, 0, 0);
}

.logo-text {
  font-size: 1.7rem;
  margin-left: -5px;
  margin-top: 12px;
  color: rgb(0, 0, 0);
}

.logo img {
  height: 40px;
}

.hamburger {
  display: none;
  flex-direction: column;
  cursor: pointer;
  gap: 5px;
}

.hamburger div {
  width: 25px;
  height: 3px;
  background-color: rgb(0, 0, 0);
}

ul.menu {
  list-style-type: none;
  padding: 0;
  display: flex;
  align-items: center;
}

li {
  margin: 0 15px;
}

.nav-link {
  color: rgb(0, 0, 0);
  text-decoration: none;
  font-size: 1.2rem;
}

.active-link {
  color: rgb(255, 128, 0);
}

.profile {
  position: relative;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.profile-photo {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  margin-right: 8px;
}

.arrow-down {
  margin-left: 8px;
  border: solid rgb(0, 0, 0);
  border-width: 0 2px 2px 0;
  display: inline-block;
  padding: 3px;
  transform: rotate(45deg);
}

.profile-menu {
  position: absolute;
  top: 140%;
  right: 0;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
  background-color: #ffffff;
  min-width: 150px;
  z-index: 10;
  display: block;
}

.profile-menu ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.profile-menu li {
  padding: 8px 15px;
  margin: 0;
  transition: background-color 0.3s ease;
}

.profile-menu li:hover {
  background-color: #f0f0f0;
}

.profile-menu a {
  color: #333;
  text-decoration: none;
  display: block;
  font-size: 1rem;
  padding: 6px 0;
  text-align: left;
}

.profile-menu a:hover {
  color: #094067;
  font-weight: 500;
}

.btn-login {
  display: inline-block;
  background-color: #094067;
  color: white;
  padding: 10px 20px;
  font-size: 1.2rem;
  text-decoration: none;
  border-radius: 5px;
  border: 2px solid #094067;
  transition: background-color 0.3s, color 0.3s;
}

.btn-login:hover {
  background-color: white;
  color: black;
}

@media (max-width: 768px) {
  nav {
    padding: 10px 20px;
  }

  .hamburger {
    display: flex;
  }

  ul.menu {
    display: none;
    flex-direction: column;
    background-color: #ffffff;
    position: absolute;
    top: 60px;
    right: 20px;
    width: 200px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    z-index: 10000;
  }

  ul.menu.menu-open {
    display: flex;
  }

  li {
    margin: 10px 0;
    text-align: center;
  }
}
</style>
