<script>
import HeaderComponent from "./components/HeaderComponent.vue";
import WelcomeComponent from "./components/WelcomeComponent.vue";
export default {
  name: "App",
  components: {
    HeaderComponent,
    WelcomeComponent
  }
}
</script>

<template>
  <HeaderComponent></HeaderComponent>
  <WelcomeComponent></WelcomeComponent>
</template>

<style scoped>

</style>