<template>
  <nav>
    <div class="logo">
      <img src="../../assets/images/logoT.png" alt="Logo" />
      <span class="logo-text">askly</span>
    </div>
    <div class="hamburger" @click="toggleMobileMenu">
      <div></div>
      <div></div>
      <div></div>
    </div>
    <ul :class="['menu', { 'menu-open': isMobileMenuOpen }]">
      <li><a href="/">Dashboard</a></li>
      <li><a href="/projetos">Projetos</a></li>
      <li><a href="/tarefas">Tarefas</a></li>
      <li><a href="/relatorios">Relatórios</a></li>
      <li class="profile" @click="toggleProfileMenu">
        <img
          class="profile-photo"
          src="http://localhost:8888/uploads/users/user-default.png"
          alt="Profile"
        />
        <span>Nome</span>
        <i class="arrow-down"></i>
        <div v-if="isProfileMenuOpen" class="profile-menu">
          <ul>
            <li><a href="/profile">Perfil</a></li>
            <li><a href="/logout">Logout</a></li>
          </ul>
        </div>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {
      isProfileMenuOpen: false,
      isMobileMenuOpen: false,
    };
  },
  methods: {
    toggleProfileMenu() {
      this.isProfileMenuOpen = !this.isProfileMenuOpen;
    },
    toggleMobileMenu() {
      this.isMobileMenuOpen = !this.isMobileMenuOpen;
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

a {
  color: rgb(0, 0, 0);
  text-decoration: none;
  font-size: 1.2rem;
}

a:hover {
  text-decoration: underline;
}

.profile {
  position: relative;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.profile-photo {
  width: 30px;
  height: 30px;
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
  top: 100%;
  right: 0;
  border-radius: 4px;
  padding: 10px;
  display: block;
}

.profile-menu ul {
  list-style-type: none;
  padding: 0;
}

.profile-menu li {
  margin: 5px 0;
}

.profile-menu a {
  color: rgb(0, 0, 0);
  text-decoration: none;
}

.profile-menu a:hover {
  text-decoration: underline;
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
