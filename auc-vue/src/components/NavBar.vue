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
                <li v-for="menuItem in item.menuItems" :key="menuItem.link">
                  <router-link
                    :to="menuItem.href"
                    active-class="active"
                    class="dropdown-item"
                  >
                    {{ menuItem.link }}</router-link
                  >
                </li>
              </ul>
            </li>
          </template>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item mx-1">
            <router-link to="/sign-up" class="btn btn-secondary"
              >Sign-up</router-link
            >
          </li>
          <li class="nav-item">
            <router-link to="/sign-in" class="btn btn-primary"
              >Login</router-link
            >
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
          menuItems: [
            { link: "All offers overview", href: "/offers/overview31" },
            { link: "Offers detail (comp)", href: "/offers/overview32" },
            { link: "Offers edit (router)", href: "/offers/overview33" },
            { link: "Offers edit (managed)", href: "/offers/overview34" },
            { link: "Offers detail (Rest-backend)", href: "/offers/overview37"}
          ],
          href: null,
        },
        {
          link: "My Bids",
          menuItems: [
            { link: "Active", href: "/myBids/active" },
            { link: "History", href: "/myBids/history" },
          ],
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
