<!-- eslint-disable vue/no-mutating-props -->
<template>
  <div>
    <form v-if="selectedOffer !== null">
      <div class="rounded-3 px-0 mb-3 border overflow-hidden w-100">
        <table class="table mb-0 table-striped">
          <thead class="text-center">
            <tr class="sticky-top">
              <th colspan="2" scope="col">
                Offer Details ID: {{ selectedOffer.id }}
              </th>
            </tr>
          </thead>
          <tbody class="text-end">
            <tr>
              <th scope="row">Title</th>
              <td class="text-start">{{ selectedOffer.title }}</td>
            </tr>
            <tr>
              <th scope="row">Description</th>
              <td class="text-start">{{ selectedOffer.description }}</td>
            </tr>
            <tr>
              <th scope="row">Status</th>
              <td class="text-start">
                {{ selectedOffer.status }}
              </td>
            </tr>
            <tr>
              <th scope="row">Sell Date</th>
              <td class="d-flex flex-column text-start gap-2">
                {{ formatDateDisplay() }}
              </td>
            </tr>
            <tr>
              <th scope="row">Latest Bid</th>
              <td class="text-start">
                {{ latestBid }}
              </td>
            </tr>
            <tr>
              <th scope="row">New bid</th>
              <td>
                <input
                  type="number"
                  class="form-control"
                  v-model.number="newBidValue"
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="d-flex justify-content-end column-gap-3 p-0 mb-3">
        <button
          type="button"
          class="btn btn-primary"
          @click="this.$router.push(this.$route.matched[0].path)"
        >
          Cancel
        </button>
        <button
          type="button"
          class="btn btn-success"
          :disabled="newBidValue <= selectedOffer.valueHighestBid"
          @click="handleSave()"
        >
          Save
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "BidsDetail45",
  emits: ["refresh-offers", "confirm-bid"],
  inject: ["offersService", "sessionService"],
  data() {
    return {
      selectedOffer: null,
      newBidValue: 0,
    };
  },
  async created() {
    await this.loadOffer();
  },
  methods: {
    async loadOffer() {
      this.selectedOffer = await this.offersService.asyncFindById(
        this.$route?.params?.id
      );

      // Set the initial bid value to the highest bid + 1.
      this.newBidValue = this.selectedOffer.valueHighestBid + 1;
    },

    async handleSave() {
      const newBid = {
        value: this.newBidValue,
        offer: {
          id: this.selectedOffer.id,
        },
        madeBy: {
          id: this.sessionService.currentAccount.id,
        },
      };

      try {
        const bid = await this.offersService.asyncAddBid(newBid);

        // If successful, emit the event to the parent component.
        this.$emit("confirm-bid", bid);

        // Push the user back to the overview.
        this.$router.push(this.$route.matched[0].path);

        this.$emit("refresh-offers");
      } catch (error) {
        console.error(error);
      }
    },

    formatDateDisplay() {
      if (this.selectedOffer.sellDate === null) return;

      /*because of timezones the displayed time is two hours lower.
      This is because of the Netherlands is two hours ahead from the greenwich time utc +0
      therefore the date needs to be offset with the timezone offset
       */
      const MILLISECONDS_IN_MINUTE = 60000;
      const timeOffsetInMilliseconds =
        this.selectedOffer.sellDate.getTime() +
        this.selectedOffer.sellDate.getTimezoneOffset() *
          MILLISECONDS_IN_MINUTE;
      const dateWithOffset = new Date(timeOffsetInMilliseconds);

      return dateWithOffset.toLocaleDateString("en-IN", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        hour12: false,
      });
    },
  },
  watch: {
    $route() {
      // If the user navigates to another offer, load the other offer.
      if (this.$route.params.id === undefined) return; // If the user navigates to the overview, do nothing.
      this.loadOffer();
    },
  },
  computed: {
    latestBid() {
      // If there is no selected offer, or the selected offer has no bids, return "No bids yet."
      if (
        !this.selectedOffer ||
        !this.selectedOffer.bids ||
        this.selectedOffer.bids.length === 0
      ) {
        return "No bids yet.";
      }

      // Find the highest bid.
      const highestBid = this.selectedOffer.bids.find(
        (bid) => bid.value === this.selectedOffer.valueHighestBid
      );

      return highestBid;
    },
  },
};
</script>

<style scoped>
tbody {
  vertical-align: middle !important;
}
</style>
