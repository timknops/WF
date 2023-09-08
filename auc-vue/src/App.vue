<template>
  <HeaderComponent />
  <WelcomeComponent auctionTitle="Random auction title" auction-description="test"/>
</template>

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



<style scoped>
</style>