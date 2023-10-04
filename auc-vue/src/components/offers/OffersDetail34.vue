<!-- eslint-disable vue/no-mutating-props -->
<template>
  <form>
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
            <td>
              <input
                class="form-control"
                type="text"
                v-model="offerCopy.title"
              />
            </td>
          </tr>
          <tr>
            <th scope="row">Description</th>
            <td>
              <input
                type="text"
                class="form-control"
                v-model="offerCopy.description"
              />
            </td>
          </tr>
          <tr>
            <th scope="row">Status</th>
            <td>
              <select class="form-select" v-model="offerCopy.status">
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
                ref="dateInput"
              />
              {{ formatDateDisplay() }}
            </td>
          </tr>
          <tr>
            <th scope="row">Highest Bid</th>
            <td>
              <input
                type="text"
                v-model="offerCopy.valueHighestBid"
                class="form-control"
              />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="d-flex justify-content-end column-gap-3 p-0 mb-3">
      <button
        class="btn btn-danger"
        :disabled="hasChanged"
        @click="$emit('deleteOffer', offerCopy)"
      >
        Delete
      </button>
      <button class="btn btn-primary" type="reset" @click="clearOffer">
        Clear
      </button>
      <button
        class="btn btn-primary"
        :disabled="!hasChanged"
        @click="resetChanges"
      >
        Reset
      </button>
      <button class="btn btn-primary" @click="cancel">Cancel</button>
      <button
        class="btn btn-success"
        :disabled="!hasChanged"
        @click="$emit('updateOffer', offerCopy)"
      >
        Save
      </button>
    </div>
  </form>
</template>

<script>
import { Offer } from "@/models/offer";

export default {
  name: "OffersDetail34",
  props: {
    selectedOffer: Offer,
  },
  data() {
    return {
      statusOptions: Object.values(Offer.Status),
      offerCopy: Offer,
    };
  },
  methods: {
    /**
     * clear all inputs of the form
     */
    clearOffer() {
      this.offerCopy.title = "";
      this.offerCopy.description = "";
      this.offerCopy.status = "";
      this.offerCopy.sellDate = null;
      this.offerCopy.valueHighestBid = 0;
    },

    /**
     * reset all changes made to the current offer
     */
    resetChanges() {
      this.offerCopy = Offer.copyConstructor(this.selectedOffer);
    },

    /**
     * Push the router back to the parent route. This results in the selectedOffer being set to null.
     */
    cancel() {
      this.$router.push(this.$route.matched[0].path);
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
  },

  computed: {
    hasChanged() {
      return !this.offerCopy.equals(this.selectedOffer);
    },

    sellDateUpdater: {
      get() {
        return new Date(this.offerCopy.sellDate).toISOString().slice(0, -8);
      },
      set(date) {
        this.offerCopy.sellDate = new Date(date);
      },
    },
  },

  created() {
    this.offerCopy = Offer.copyConstructor(this.selectedOffer);
  },

  watch: {
    selectedOffer() {
      this.offerCopy = Offer.copyConstructor(this.selectedOffer);
    },
  },
};
</script>

<style scoped>
tbody {
  vertical-align: middle !important;
}
</style>
