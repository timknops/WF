<template>
  <div
    class="modal fade bg-dark bg-opacity-25 show d-block"
    id="static-modal"
    data-bs-backdrop="static"
    data-bs-keyboard="false"
    tabindex="-1"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">{{ title }}</h5>
          <button
            @click="$emit('cornerCloseModalBtn')"
            type="button"
            class="btn-close"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">{{ text }}</div>
        <div class="modal-footer">
          <button
            @click="$emit('cancelModalBtn')"
            type="button"
            class="btn btn-secondary"
          >
            Cancel
          </button>
          <button
            @click="$emit('okModalBtn')"
            type="button"
            class="btn btn-primary"
          >
            OK
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ModalComponent",
  props: {
    title: String,
    text: String,
  },
};
</script>

<style scoped></style>
