<template>
  <div class="container-fluid bg-light px-5 w-100 w-auto">
    <div class="row">
      <div class="col-3 flex-column">
        <h4>Pick a date</h4>
        <input type="date" id="date">
        <h4>Pick a category</h4>
      </div>
      <div class="col">
        <h4>Auctions that expire today</h4>
      </div>
      <div class="col-3">
        <h4>{{ auctionTitle }}</h4>
        <p>{{ auctionDescription }}</p>
        <h6>{{ auctionDueDate }}</h6>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "WelcomeComponent",
  props: {
    auctionImages: Array,
    auctionTitle: String,
    auctionDescription: String,
    auctionDueDate: Date,
  }
}
</script>

<style scoped>
.container {
  margin: 0;
}

.col-3 {
  width: 300px;
}
</style>