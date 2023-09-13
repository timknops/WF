<template>
  <h2 class="container mt-5 p-0 fw-bold text-primary">List of all offers</h2>
  <div
    class="table-container container rounded-3 my-3 px-0 border overflow-hidden"
  >
    <table class="table mb-0 table-striped">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Title</th>
          <th scope="col">Selling Date</th>
          <th scope="col">Status</th>
          <th scope="col">Value Highest Bid</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="offer in offers" :key="offer.id">
          <th scope="row">{{ offer.id }}</th>
          <td>{{ offer.title }}</td>
          <td>{{ offer.sellingDate }}</td>

          <!-- If status is not null, then display, else leave empty -->
          <td v-if="offer.status !== 'NEW'">{{ offer.status }}</td>
          <td v-else></td>
          <td>{{ offer.highestBid }}</td>
        </tr>
      </tbody>
    </table>
  </div>
  <div class="container d-flex justify-content-end p-0">
    <button class="btn btn-primary" @click="onNewOffer()">New Offer</button>
  </div>
</template>

<script>
export default {
  name: "OffersOverview31",
  data() {
    return {
      offers: [],
    };
  },
  methods: {
    onNewOffer() {
      // Should add an offer to the offers array.
      console.log("TEST");
    },
  },
};
</script>

<style scoped></style>
