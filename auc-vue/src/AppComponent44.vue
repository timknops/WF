<template>
  <HeaderComponent />
  <NavBar />
  <router-view />
</template>

<script>
import HeaderComponent from "./components/HeaderSbComponent.vue";
import NavBar from "@/components/NavBarSb.vue";
import { OffersAdaptor } from "@/service/offersAdaptor";
import Config from "@/config.js";
import { SessionSbService } from "@/service/sessionSbService";
import { FetchInterceptor } from "@/service/fetchInterceptor";
import { shallowReactive } from "vue";

export default {
  name: "AppComponent44",
  components: {
    NavBar,
    HeaderComponent,
  },
  provide() {
    this.theSessionService = shallowReactive(
      new SessionSbService(
        `${Config.BACKEND_URL}/authentication`,
        Config.JWT_STORAGE_ITEM
      )
    );

    this.theFetchInterceptor = new FetchInterceptor(
      this.theSessionService,
      this.$router
    );

    return {
      offersService: new OffersAdaptor(`${Config.BACKEND_URL}/offers`),
      sessionService: this.theSessionService,
    };
  },

  beforeUnmount() {
    this.theFetchInterceptor.unregister();
  },
};
</script>

<style scoped></style>
