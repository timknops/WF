<template>
  <div class="container d-flex mt-5">
    <div class="w-50 mx-5">
      <h2 class="p-0 fw-bold text-primary">All offers (on sale)</h2>
      <p v-if="confirmMessage" class="py-3 text-success">
        {{ confirmMessage }}
      </p>
      <div
        class="rounded-3 my-3 px-0 border overflow-x-hidden overflow-y-scroll table-container"
        ref="tableDiv"
      >
        <table class="table mb-0 table-striped text-center">
          <thead>
            <tr class="sticky-top">
              <th scope="col">Title</th>
              <th scope="col">Highest Bid</th>
              <th scope="col">Made By</th>
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
              <td>{{ offer.valueHighestBid }}</td>
              <td>{{ highestBidder(offer) }}</td>
            </tr>
          </tbody>
        </table>
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
        @refresh-offers="loadOffers"
        @confirm-bid="handleConfirmBid"
      />
    </div>
  </div>
</template>

<script>
import { Offer } from "@/models/offer";

export default {
  name: "BidsOverview45",
  inject: ["offersService"],
  data() {
    return {
      offers: [],
      selectedOffer: null,
      confirmMessage: "",
    };
  },
  async created() {
    // Get all offers with the status "FOR_SALE.
    this.loadOffers();
  },
  methods: {
    async createNewOffer() {
      return await this.offersService.asyncSave(Offer.createEmptyOffer());
    },

    setSelectedOffer(offer) {
      //return to main screen when offer is deselected
      if (offer === this.selectedOffer) {
        this.$router.push(this.$route.matched[0].path);
        this.selectedOffer = null;
        return;
      }

      //if the offer exist add the id to the param
      if (offer != null) {
        this.selectedOffer = offer;
        this.$router.push(this.$route.matched[0].path + "/" + offer.id);
      }
    },

    async loadOffers() {
      this.offers = await this.offersService.asyncFindAll({
        status: "FOR_SALE",
      });
    },

    highestBidder(offer) {
      if (!offer || !offer.bids || offer.bids.length === 0) {
        return "";
      }

      // Find the highest bid.
      const highestBid = offer.bids.find(
        (bid) => bid.value === offer.valueHighestBid
      );

      return highestBid.madeBy.name;
    },

    async handleConfirmBid(offer) {
      // Format date to example: Aug, 12 Aug 2022
      const formattedDate = new Date(offer.sellDate).toLocaleString("en-IN", {
        weekday: "long",
        day: "numeric",
        month: "short",
        year: "numeric",
      });

      this.confirmMessage = `Submitted bid id=${offer.bid.id} on offer ${offer.bid.offer.title} of value ${offer.bid.value} by ${offer.bid.madeBy.name} ending at ${formattedDate}`;

      setTimeout(() => {
        this.confirmMessage = "";
      }, 5000);
    },
  },
  watch: {
    $route() {
      //if the offer doesn't exist remove the id from the url
      if (this.selectedOffer === undefined) {
        this.$router.push(this.$route.matched[0].path);
      }

      // Remove the selected offer when the user navigates to the overview.
      if (this.$route.params.id === undefined) {
        this.selectedOffer = null;
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
