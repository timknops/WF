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
              @click="setSelectedOffer(offer)"
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
          v-if="selectedOffer === null || selectedOffer === undefined"
          class="h-100 d-flex align-items-center"
      >
        <p>Select an offer at the left</p>
      </div>
      <router-view
          v-else
          @delete-offer="deleteOffer"
          @update-offer="updateOffer"
          :selectedOffer="selectedOffer"
      />
    </div>
  </div>
</template>

<script>
import { Offer } from "@/models/offer";

export default {
  name: "OffersOverview37",
  inject: ['offersService'],
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
      this.$router.push(
          this.$route.matched[0].path + "/" + this.selectedOffer.id
      );
    },

    setSelectedOffer(offer) {
      //return to main screen when offer is deselected
      if (offer === this.selectedOffer) {
        this.$router.push(this.$route.matched[0].path);
        return;
      }

      //if the offer exist add the id to the param
      if (offer != null) {
        this.selectedOffer = offer;
        this.$router.push(
            this.$route.matched[0].path + "/" + this.selectedOffer.id
        );
      }
    },

    deleteOffer(offerToBeDeleted) {
      this.offers = this.offers.filter(
          (offer) => !offer.equals(offerToBeDeleted)
      ); // Remove the item from the array of offers.
      this.$router.push(this.$route.matched[0].path);
    },

    /**
     * update the selected offer with the new changes which were made
     * @param {Offer} updatedOffer - an offer object with changes
     */
    updateOffer(updatedOffer) {
      //find the index of the offer which is being updated and update the list
      const index = this.offers.findIndex((offer) => {
        return offer.id === updatedOffer.id;
      });
      this.offers[index] = updatedOffer;
      this.$router.push(this.$route.matched[0].path);
    },

    findOfferById(id) {
      // The id parameter must be of type integer!
      return this.offers.find((offer) => offer.id === id);
    },
  },
  async created() {
    this.offers = await this.offersService.asyncFindAll();
  },
  watch: {
    $route() {
      this.selectedOffer = this.findOfferById(parseInt(this.$route.params.id));

      //if the offer doesn't exist remove the id from the url
      if (this.selectedOffer === undefined) {
        this.$router.push(this.$route.matched[0].path);
      }
    },
  },
};
</script>

<style scoped>
.table-container {
  max-height: 50vh;
}

.sticky-top {
  z-index: 1;
}

td {
  cursor: pointer;
}

.table-active {
  --bs-table-active-bg: var(--bs-primary-bg-subtle);
}
</style>