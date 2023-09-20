<!-- eslint-disable vue/no-mutating-props -->
<template>
  <form>
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
            <td>
              <input
                class="form-control"
                type="text"
                v-model="selectedOffer.title"
              />
            </td>
          </tr>
          <tr>
            <th scope="row">Description</th>
            <td>
              <input
                type="text"
                class="form-control"
                v-model="selectedOffer.description"
              />
            </td>
          </tr>
          <tr>
            <th scope="row">Status</th>
            <td>
              <select class="form-select" v-model="selectedOffer.status">
                <option v-for="status in statusOptions" :key="status">
                  {{ status }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <th scope="row">Sell Date</th>
            <td class="d-flex flex-column text-start gap-2">
              <input
                class="form-control"
                type="datetime-local"
                v-model="sellDateUpdater"
              />
              {{ formatDateDisplay() }}
            </td>
          </tr>
          <tr>
            <th scope="row">Highest Bid</th>
            <td>
              <input
                type="text"
                v-model="selectedOffer.valueHighestBid"
                class="form-control"
              />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="d-flex justify-content-end p-0 mb-3">
      <button
        class="btn btn-primary"
        @click="$emit('deleteOffer', selectedOffer)"
      >
        Delete
      </button>
    </div>
  </form>
</template>

<script>
import { Offer } from "@/models/offer";

export default {
  name: "OffersDetail32",
  props: {
    selectedOffer: Offer,
  },
  data() {
    return {
      statusOptions: Object.values(Offer.Status),
    };
  },
  methods: {
    formatDateDisplay() {
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
  computed: {
    sellDateUpdater: {
      get() {
        return new Date(this.selectedOffer.sellDate).toISOString().slice(0, -8);
      },
      set(date) {
        this.selectedOffer.sellDate = new Date(date);
      },
    },
  },
};
</script>

<style scoped>
tbody {
  vertical-align: middle !important;
}
</style>
