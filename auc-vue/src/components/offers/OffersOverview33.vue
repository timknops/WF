<template>
  <div class="container d-flex mt-5">
    <div class="w-25 mx-5">
      <h2 class="p-0 fw-bold text-primary">List of all offers</h2>
      <div
        class="rounded-3 my-3 px-0 border overflow-x-hidden overflow-y-scroll table-container"
        ref="tableDiv"
      >
        <table class="table mb-0 table-striped text-center">
          <thead>
            <tr class="sticky-top">
              <th scope="col">Title</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="offer in offers"
              :key="offer.id"
              :class="{ 'table-active': selectedOffer === offer }"
              @click="onSelect(offer)"
            >
              <td>{{ offer.title }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="d-flex justify-content-end p-0 mb-3">
        <button class="btn btn-primary" @click="onNewOffer()">New Offer</button>
      </div>
    </div>
    <div class="w-75 mt-5 pt-2">
      <div
        v-if="selectedOffer === null"
        class="h-100 d-flex align-items-center"
      >
        <p>Select an offer at the left</p>
      </div>
      <router-view v-else :selectedOffer="selectedOffer" />
    </div>
  </div>
</template>

<script>
import { Offer } from "@/models/offer";

export default {
  name: "OffersOverview33",
  data() {
    return {
      offerId: 30000,
      offers: [],
      selectedOffer: null,
    };
  },
  methods: {
    createNewOffer() {
      return Offer.createSampleOffer((this.offerId += 3));
    },

    onNewOffer() {
      this.offers.push(this.createNewOffer());

      // Waits until the DOM is updated before updating the scroll position.
      this.$nextTick(() => {
        this.$refs.tableDiv.scrollTop = this.$refs.tableDiv.scrollHeight;
      });

      this.selectedOffer = this.offers[this.offers.length - 1];
    },

    setSelectedOffer(offer) {
      if (offer === this.selectedOffer) {
        this.selectedOffer = null;
        return;
      }

      this.selectedOffer = offer;
    },

    deleteOffer(offerToBeDeleted) {
      this.offers = this.offers.filter((offer) => offer !== offerToBeDeleted); // Remove the item from the array of offers.
      this.selectedOffer = null;
    },

    onSelect(offer) {
      if (offer !== null && offer !== this.selectedOffer) {
        this.$router.push(this.$route.matched[0].path + "/" + offer.id);
      } else if (this.selectedOffer !== null) {
        this.$router.back();
      }

      this.setSelectedOffer(offer);
    },
  },
  created() {
    const OFFER_LIST_LENGTH = 8;

    for (let i = 0; i < OFFER_LIST_LENGTH; i++) {
      this.offers.push(this.createNewOffer());
    }
  },
  watch: {
    $router() {
      this.selectedOffer = this.$route.params.id;
    },
  },
};
</script>

<style scoped>
.table-container {
  max-height: 50vh;
}

td {
  cursor: pointer;
}

.table-active {
  --bs-table-active-bg: var(--bs-primary-bg-subtle);
}
</style>
