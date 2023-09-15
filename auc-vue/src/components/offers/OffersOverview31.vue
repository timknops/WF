<template>
  <h2 class="container mt-4 p-0 fw-bold text-primary">List of all offers</h2>
  <div
    class="table-container container rounded-3 my-3 px-0 border overflow-x-hidden overflow-y-scroll"
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
          <td>{{ offer.sellDate }}</td>
          <td>{{ offer.status }}</td>

          <!-- If status is not null, then display, else leave empty -->
          <td v-if="offer.status !== 'NEW'">€ {{ offer.valueHighestBid }}</td>
          <td v-else></td>
        </tr>
      </tbody>
    </table>
  </div>
  <div class="container d-flex justify-content-end p-0 mb-3">
    <button class="btn btn-primary" @click="onNewOffer()">New Offer</button>
  </div>
</template>

<script>
import { Offer } from "@/models/offer";

export default {
  name: "OffersOverview31",
  data() {
    return {
      offers: [],
      offerId: 30000,
    };
  },
  methods: {
    onNewOffer() {
      this.offers.push(this.createNewOffer());
    },

    createNewOffer() {
      let newOffer = Offer.createSampleOffer((this.offerId += 3));

      newOffer.sellDate = newOffer.sellDate.toLocaleDateString("en-IN", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      });

      return newOffer;
    },
  },
  created() {
    const OFFER_LIST_LENGTH = 8;

    for (let i = 0; i < OFFER_LIST_LENGTH; i++) {
      this.offers.push(this.createNewOffer());
    }
  },
};
</script>

<style scoped>
.table-container {
  max-height: 50vh;
}
</style>
