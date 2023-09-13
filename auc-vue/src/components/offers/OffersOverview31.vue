<template>
  <h3 class="container mt-5 p-0 fw-bold">List of all offers</h3>
  <div
    class="table-container container rounded-3 mt-3 px-0 border overflow-hidden"
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
          <td>{{ offer.sellingDate }}</td>
          <td>{{ offer.status }}</td>
          <td>{{ offer.highestBid }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "OffersOverview31",
  data() {
    return {
      offers: [],
    };
  },
};
</script>

<style scoped></style>
