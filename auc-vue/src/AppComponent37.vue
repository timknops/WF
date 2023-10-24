<template>
  <HeaderComponent />
  <NavBar />
  <router-view />
</template>

<script>
import HeaderComponent from "./components/HeaderComponent.vue";
import NavBar from "@/components/NavBar.vue";

export default {
  name: "AppComponent37",
  components: {
    NavBar,
    HeaderComponent,
  },
};
</script>

<style scoped></style>