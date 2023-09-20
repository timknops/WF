<template>
  <nav class="navbar navbar-expand-md bg-body-tertiary px-5 border-bottom">
    <div class="container-fluid p-0">
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNavDropdown"
        aria-controls="navbarNavDropdown"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div
        class="collapse navbar-collapse justify-content-between"
        id="navbarNavDropdown"
      >
        <ul class="navbar-nav">
          <!-- using a template to make sure i can loop over all links, and create the correct list item in the navbar -->
          <template v-for="item in navItemsLeft" :key="item.link">
            <li class="nav-item" v-if="item.menuItems.length === 0">
              <!-- if there is no href declared redirect to the homepage -->
              <router-link
                :to="item.href"
                active-class="active"
                class="nav-link px-0 me-4 text-dark"
                >{{ item.link }}</router-link
              >
            </li>
            <li class="nav-item dropdown" v-else>
              <a
                class="nav-link dropdown-toggle px-0 me-4 text-dark"
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                {{ item.link }}
              </a>
              <ul class="dropdown-menu">
                <li v-for="menuItem in item.menuItems" :key="menuItem">
                  <a href="#" class="dropdown-item"> {{ menuItem }}</a>
                </li>
              </ul>
            </li>
          </template>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item mx-1">
            <button class="btn btn-secondary">Sign-up</button>
          </li>
          <li class="nav-item">
            <button class="btn btn-primary">Login</button>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "NavBar",
  computed: {},
  data() {
    return {
      navItemsLeft: [
        {
          link: "Home",
          menuItems: [],
          href: "/home",
        },
        {
          link: "My Offers",
          menuItems: [],
          href: "/offers/overview31",
        },
        {
          link: "My Bids",
          menuItems: ["Active", "History"],
          href: null,
        },
        {
          link: "My Account",
          menuItems: [],
          href: "/account",
        },
      ],
    };
  },
};
</script>
<style scoped>
.nav-link:hover {
  color: var(--bs-gray-700) !important;
}
.nav-link.active {
  color: var(--bs-primary) !important;
  font-weight: bold;
}
</style>
