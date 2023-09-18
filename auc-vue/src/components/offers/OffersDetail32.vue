<!-- eslint-disable vue/no-mutating-props -->
<template>
  <input type="text" v-model="offer.title" />
  <input type="text" v-model="offer.description" />
</template>

<script>
import { Offer } from "@/models/offer";

export default {
  name: "OffersDetail32",
  props: {
    offer: Offer,
  },
};
</script>

<style scoped></style>
