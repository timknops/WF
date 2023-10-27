<template>
  <HeaderComponent />
  <NavBar />
  <router-view />
</template>

<script>
import HeaderComponent from "./components/HeaderComponent.vue";
import NavBar from "@/components/NavBar.vue";
import { OffersAdaptor } from "@/service/offersAdaptor";
import Config from "@/config.js";

export default {
  name: "AppComponent37",
  components: {
    NavBar,
    HeaderComponent,
  },
  provide() {
    return {
      offersService: new OffersAdaptor(`${Config.BACKEND_URL}/offers`),
    };
  },
};
</script>

<style scoped></style>
