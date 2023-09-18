<template>
  <div class="w-25 mx-5">
    <h2 class="mt-5 p-0 fw-bold text-primary">List of all offers</h2>
    <div
      class="rounded-3 my-3 px-0 border overflow-x-hidden overflow-y-scroll"
      ref="tableDiv"
    >
      <table class="table mb-0 table-striped">
        <thead>
          <tr class="sticky-top">
            <th scope="col">Title</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="offer in offers" :key="offer.id">
            <td>{{ offer.title }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="d-flex justify-content-end p-0 mb-3">
      <button class="btn btn-primary" @click="onNewOffer()">New Offer</button>
    </div>
  </div>
</template>

<script>
import { Offer } from "@/models/offer";

export default {
  name: "OffersOverview32",
  data() {
    return {
      offers: [],
    };
  },
  methods: {
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

    onNewOffer() {
      this.offers.push(this.createNewOffer());

      // Waits until the DOM is updated before updating the scroll position.
      this.$nextTick(() => {
        this.$refs.tableDiv.scrollTop = this.$refs.tableDiv.scrollHeight;
      });
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

<style scoped></style>
