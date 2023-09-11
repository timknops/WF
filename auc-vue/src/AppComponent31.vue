<template>
  <HeaderComponent />
  <NavBar />
  <WelcomeComponent />
</template>

<script>
import HeaderComponent from "./components/HeaderComponent.vue";
import WelcomeComponent from "./components/WelcomeComponent.vue";
import NavBar from "@/components/NavBar.vue";
export default {
  name: "App",
  components: {
    NavBar,
    HeaderComponent,
    WelcomeComponent,
  },
};
</script>

<style scoped></style>
