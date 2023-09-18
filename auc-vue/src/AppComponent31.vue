<template>
  <HeaderComponent />
  <NavBar />
  <OffersOverview32 />
</template>

<script>
import HeaderComponent from "./components/HeaderComponent.vue";
import NavBar from "@/components/NavBar.vue";
import OffersOverview32 from "@/components/offers/OffersOverview32.vue";

export default {
  name: "App",
  components: {
    OffersOverview32,
    NavBar,
    HeaderComponent,
  },
};
</script>

<style scoped></style>
