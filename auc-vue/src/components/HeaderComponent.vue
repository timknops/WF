<template>
  <header class="w-100 container-fluid bg-body-tertiary border-bottom px-5">
    <div class="d-flex">
      <div class="left-img p-1 border-light">
        <img
          src="../assets/gavel.jpg"
          class="rounded"
          alt="picture of a gavel"
        />
      </div>
      <div class="center col">
        <div class="title pb-1 border-bottom mt-2">
          <h1 class="text-center fw-bold text-primary">{{ title }}</h1>
        </div>
        <div class="sub-title align-items-center row px-2 py-2">
          <h6 class="col m-0 my-2">Today is {{ getCurrentDate }}</h6>
          <h6 class="col text-end m-0 my-2">{{ subtitle }}</h6>
        </div>
      </div>
      <div class="right-img p-1">
        <img
          src="../assets/auction-paddle.jpg"
          class="rounded"
          alt="Auction bidding paddle"
        />
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "HeaderComponent",
  data() {
    return {
      title: "Auctioneer",
      subtitle: "Powered to you by hva.nl",
    };
  },
  computed: {
    getCurrentDate() {
      const date = new Date();

      return date.toLocaleDateString("en-IN", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      });
    },
  },
};
</script>

<style scoped>
img {
  object-fit: cover;
  aspect-ratio: 1;
  height: 100px;
}
</style>
