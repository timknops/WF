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
        type="button"
        class="btn btn-danger"
        :disabled="hasChanged"
        @click="handleModal(CLICKED_BUTTON_OPTIONS.DELETE, 1)"
      >
        Delete
      </button>
      <button
        type="button"
        class="btn btn-primary"
        @click="handleModal(CLICKED_BUTTON_OPTIONS.CLEAR, 0)"
      >
        Clear
      </button>
      <button
        type="reset"
        class="btn btn-primary"
        :disabled="!hasChanged"
        @click="handleModal(CLICKED_BUTTON_OPTIONS.RESET, 0)"
      >
        Reset
      </button>
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
        :disabled="!hasChanged"
        @click="handleSave()"
      >
        Save
      </button>
    </div>
  </form>
  <Transition name="fade">
    <ModalComponent
      v-if="showModal"
      :title="modalTitle"
      :text="modalText"
      @cancel-modal-btn="this.showModal = false"
      @corner-close-modal-btn="this.showModal = false"
      @ok-modal-btn="continueButtonAction"
    />
  </Transition>
</template>

<script>
import { Offer } from "@/models/offer";
import ModalComponent from "@/components/ModalComponent.vue";

export default {
  name: "OffersDetail37",
  components: { ModalComponent },
  inject: ["offersService"],
  props: {
    selectedOffer: Offer,
  },
  emits: ["deleteOffer", "updateOffer"],
  data() {
    return {
      statusOptions: Object.values(Offer.Status),
      offerCopy: Offer,
      showModal: false,
      modalTitle: "",
      modalText: "",
      currentButtonClicked: "",
      CLICKED_BUTTON_OPTIONS: Object.freeze({
        CANCEL: "CANCEL",
        CLEAR: "CLEAR",
        RESET: "RESET",
        DELETE: "DELETE",
        DISCARD: "DISCARD",
      }),
      navigateTo: "",
      leaveValidated: false,
    };
  },
  methods: {
    handleSave() {
      this.$emit("updateOffer", this.offerCopy);
    },

    clearInputs() {
      this.offerCopy.title = "Empty Offer";
      this.offerCopy.description = "";
      this.offerCopy.status = this.statusOptions[0];
      this.offerCopy.sellDate = null;
      this.offerCopy.valueHighestBid = 0;
    },

    handleModal(buttonVersion, textVersion) {
      this.currentButtonClicked = buttonVersion;

      if (
        this.hasChanged ||
        this.currentButtonClicked === this.CLICKED_BUTTON_OPTIONS.DELETE
      ) {
        this.setModalParameters(textVersion);
        this.showModal = true;

        return;
      }

      this.continueButtonAction();
    },

    continueButtonAction() {
      switch (this.currentButtonClicked) {
        case this.CLICKED_BUTTON_OPTIONS.CANCEL:
          this.leaveValidated = true;
          this.$router.push(this.$route.matched[0].path);
          break;

        case this.CLICKED_BUTTON_OPTIONS.CLEAR:
          this.clearInputs();
          break;

        case this.CLICKED_BUTTON_OPTIONS.RESET:
          this.offerCopy = Offer.copyConstructor(this.selectedOffer);
          break;

        case this.CLICKED_BUTTON_OPTIONS.DELETE:
          this.$emit("deleteOffer", this.offerCopy);
          break;

        case this.CLICKED_BUTTON_OPTIONS.DISCARD:
          // If the user clicked the discard button, the changes are discarded and the user is navigated to the route
          // that was saved before the user clicked the discard button.
          this.leaveValidated = true;
          this.$router.push(this.navigateTo);
          break;
      }

      this.showModal = false;
    },

    setModalParameters(textVersion) {
      const MODAL_TEXT = [
        `Are you sure to discard unchanged changes in ${this.offerCopy.title}? (id=${this.offerCopy.id})`,
        `Are you sure to delete offer ${this.offerCopy.title}? (id=${this.offerCopy.id})`,
      ];

      this.modalTitle =
        this.currentButtonClicked.charAt(0) +
        this.currentButtonClicked.slice(1).toLowerCase();
      this.modalText = MODAL_TEXT[textVersion];
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

  computed: {
    hasChanged() {
      return !this.selectedOffer.equals(this.offerCopy);
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

  // If the user navigates to another offer, ask the user if they want to discard the changes.
  async beforeRouteUpdate(to, from, next) {
    const previousSelectedOffer = await this.offersService.asyncFindById(
      parseInt(from.params.id)
    );

    if (this.leaveValidated) {
      this.offerCopy = Offer.copyConstructor(this.selectedOffer);

      this.leaveValidated = false;
      next();
      return;
    }

    // If there have been changes, ask the user if they want to discard them.
    if (!previousSelectedOffer.equals(this.offerCopy)) {
      this.currentButtonClicked = this.CLICKED_BUTTON_OPTIONS.DISCARD;
      this.setModalParameters(0);
      this.showModal = true;
      this.navigateTo = to.path; // Save the path to navigate to after the user has discarded the changes.

      next(false);
    } else {
      this.offerCopy = Offer.copyConstructor(this.selectedOffer);
      next();
    }
  },

  beforeRouteLeave(to, from, next) {
    if (this.leaveValidated) {
      next();

      return;
    }

    // If changes have been made, ask the user if they want to discard them.
    if (this.hasChanged) {
      this.currentButtonClicked = this.CLICKED_BUTTON_OPTIONS.DISCARD;
      this.setModalParameters(0);
      this.showModal = true;
      this.navigateTo = to.path; // Save the path to navigate to after the user has discarded the changes.

      next(false); // Prevent the user from leaving the page.
    } else {
      next(); // Allow the user to leave the page.
    }
  },

  mounted() {
    window.addEventListener("beforeunload", this.beforeUnload);
  },

  beforeUnmount() {
    window.removeEventListener("beforeunload", this.beforeUnload);
  },

  // Force a reload of the page when changes are made.
  unmounted() {
    if (this.hasChanged || this.currentButtonClicked === "DELETE") {
      window.location.reload();
    }
  },
};
</script>

<style scoped>
tbody {
  vertical-align: middle !important;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease-in-out;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
