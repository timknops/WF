<template>
  <div class="container text-center mt-5">
    <h2 class="fw-bold">
      404 <br />
      page not found
    </h2>
    <p class="fs-4">
      There is no component available at route {{ $route.path }}
    </p>
  </div>
</template>

<script>
export default {
  name: "UnknownRoute",
};
</script>

<style scoped></style>
