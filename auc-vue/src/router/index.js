import { createRouter, createWebHashHistory } from "vue-router";
import WelcomeComponent from "@/components/WelcomeComponent.vue";
import OffersOverview31 from "@/components/offers/OffersOverview31.vue";
import OffersOverview32 from "@/components/offers/OffersOverview32.vue";
import UnknownRoute from "@/components/UnknownRoute.vue";
import OffersOverview33 from "@/components/offers/OffersOverview33.vue";

const routes = [
  { path: "/", component: WelcomeComponent },
  { path: "/home", component: WelcomeComponent },
  { path: "/offers/overview31", component: OffersOverview31 },
  { path: "/offers/overview32", component: OffersOverview32 },
  { path: "/offers/overview33", component: OffersOverview33 },
  { path: "/:pregMatch(.*)", component: UnknownRoute },
];

export const router = createRouter({
  history: createWebHashHistory(),
  routes,
});
