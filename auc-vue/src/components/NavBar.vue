<template>
  {{ Offer.createSampleOffer(2) }}
  <nav class="navbar navbar-expand-md bg-body-tertiary px-5">
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
          <template v-for="item in navItemsLeft" :key="item.link">
            <li class="nav-item" v-if="item.menuItems.length === 0">
              <a href="#" class="nav-link px-0 me-4 text-dark">{{
                item.link
              }}</a>
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
import { Offer } from "../models/offer";

export default {
  name: "NavBar",
  computed: {
    Offer() {
      return Offer;
    },
  },
  data() {
    return {
      navItemsLeft: [
        {
          link: "Home",
          menuItems: [],
        },
        {
          link: "My Offers",
          menuItems: [],
        },
        {
          link: "My Bids",
          menuItems: ["Active", "History"],
        },
        {
          link: "My Account",
          menuItems: [],
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
</style>
