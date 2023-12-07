<template>
  <HeaderComponent />
  <NavBar />
  <router-view />
</template>

<script>
import HeaderComponent from "./components/HeaderSbComponent.vue";
import NavBar from "@/components/NavBar.vue";
import { OffersAdaptor } from "@/service/offersAdaptor";
import Config from "@/config.js";
import {SessionSbService} from "@/service/sessionSbService";
import {shallowReactive} from "vue";

export default {
  name: "AppComponent44",
  components: {
    NavBar,
    HeaderComponent,
  },
  provide() {
    this.theSessionSevice = shallowReactive(new SessionSbService(`${Config.BACKEND_URL}/authentication`, Config.JWT_STORAGE_ITEM));
    return {
      offersService: new OffersAdaptor(`${Config.BACKEND_URL}/offers`),
      sessionService: this.theSessionSevice,
    };
  },
};
</script>

<style scoped></style>