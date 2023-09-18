<!-- eslint-disable vue/no-mutating-props -->
<template>
  <div
    class="rounded-3 my-3 px-0 border overflow-x-hidden overflow-y-scroll table-container"
  >
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
          <td></td>
        </tr>
      </tbody>
    </table>
  </div>
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
      return this.selectedOffer.sellDate.toLocaleDateString("en-IN", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
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

.table-container {
  height: 50vh;
}
</style>
