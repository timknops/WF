<script>
export default {
  name: "HeaderComponent",
  data() {
    return {
      title: "Auctioneer",
      subtitle: "Powered to you by HVA",
    }
  }
}
</script>

<template>
  <header class="w-100 container-fluid bg-secondary text-white">
    <div class="d-flex">
      <div class="left-img p-2 border-light">
        <img src="../assets/gavel.jpg" class="rounded" alt="picture of a gavel">
      </div>
      <div class="center p-2 col">
        <div class="title pb-2 border-light border-bottom">
          <h1 class="text-center">{{ title }}</h1>
        </div>
        <div class="sub-title">
          <h2 class="text-end">{{ subtitle}}</h2>
        </div>
      </div>
      <div class="right-img p-2">
        <img src="../assets/auction-paddle.jpg" class="rounded" alt="Auction bidding paddle">
      </div>
    </div>
  </header>

</template>

<style scoped>

img {
  object-fit: cover;
  aspect-ratio: 1;
  height: 125px;
}

</style>