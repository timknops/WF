<!-- eslint-disable vue/no-mutating-props -->
<template>
  <div>
    <form v-if="selectedOffer !== null">
      <div class="rounded-3 px-0 mb-3 border overflow-hidden w-100">
        <table class="table mb-0 table-striped">
          <thead class="text-center">
            <tr class="sticky-top">
              <th colspan="2" scope="col">
                Offer Details ID: {{ offerCopy.id }}
              </th>
            </tr>
          </thead>
          <tbody class="text-end">
            <tr>
              <th scope="row">Title</th>
              <td class="text-start">{{ offerCopy.title }}</td>
            </tr>
            <tr>
              <th scope="row">Description</th>
              <td class="text-start">{{ offerCopy.description }}</td>
            </tr>
            <tr>
              <th scope="row">Status</th>
              <td class="text-start">
                {{ offerCopy.status }}
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
          @click="handleModal(CLICKED_BUTTON_OPTIONS.CANCEL, 0)"
        >
          Cancel
        </button>
        <button
          type="button"
          class="btn btn-success"
          :disabled="newBidValue <= offerCopy.valueHighestBid"
          @click="handleModal(CLICKED_BUTTON_OPTIONS.SAVE, 0)"
        >
          Save
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import { Offer } from "@/models/offer";

export default {
  name: "BidsDetail45",
  emits: ["refresh-offers"],
  inject: ["offersService"],
  data() {
    return {
      selectedOffer: null,
      offerCopy: Offer,
      showModal: false,
      modalTitle: "",
      modalText: "",
      currentButtonClicked: "",
      CLICKED_BUTTON_OPTIONS: Object.freeze({
        CANCEL: "CANCEL",
        SAVE: "SAVE",
      }),
      navigateTo: "",
      leaveValidated: false,
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

      this.offerCopy = Offer.copyConstructor(this.selectedOffer);
      this.newBidValue = this.offerCopy.valueHighestBid + 1;
    },

    async handleSave() {
      // If the title is empty, set it to "Empty Offer".
      if (this.offerCopy.title === "") {
        this.offerCopy.title = "Empty Offer";
      }

      await this.offersService.asyncSave(this.offerCopy);
      this.$router.push(this.$route.matched[0].path);

      this.$emit("refresh-offers"); // Refresh the offer list.
    },

    formatDateDisplay() {
      if (this.offerCopy.sellDate === null) return;

      /*because of timezones the displayed time is two hours lower.
      This is because of the Netherlands is two hours ahead from the greenwich time utc +0
      therefore the date needs to be offset with the timezone offset
       */
      const MILLISECONDS_IN_MINUTE = 60000;
      const timeOffsetInMilliseconds =
        this.offerCopy.sellDate.getTime() +
        this.offerCopy.sellDate.getTimezoneOffset() * MILLISECONDS_IN_MINUTE;
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

    // Prevent user from leaving the page without saving changes.
    beforeUnload(e) {
      if (!this.leaveValidated && this.hasChanged) {
        // Cancel the event.
        e.preventDefault();

        // Chrome requires returnValue to be set.
        e.returnValue = "";
      }
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
      if (
        !this.offerCopy ||
        !this.offerCopy.bids ||
        this.offerCopy.bids.length === 0
      ) {
        return "No bids yet.";
      }

      // Find the highest bid.
      const highestBid = this.offerCopy.bids.find(
        (bid) => bid.value === this.offerCopy.valueHighestBid
      );

      return highestBid;
    },
  },

  mounted() {
    window.addEventListener("beforeunload", this.beforeUnload);
  },

  beforeUnmount() {
    window.removeEventListener("beforeunload", this.beforeUnload);
  },
};
</script>

<style scoped>
tbody {
  vertical-align: middle !important;
}
</style>
