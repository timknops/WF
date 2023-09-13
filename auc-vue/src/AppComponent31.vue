<template>
  <HeaderComponent />
  <NavBar />
  <OffersOverview31 />
</template>

<script>
import HeaderComponent from "./components/HeaderComponent.vue";
import NavBar from "@/components/NavBar.vue";
import OffersOverview31 from "@/components/offers/OffersOverview31.vue";

export default {
  name: "App",
  components: {
    OffersOverview31,
    NavBar,
    HeaderComponent,
  },
};
</script>

<style scoped></style>
