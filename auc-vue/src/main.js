import { createApp } from "vue";
import App from "./AppComponent31.vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import "./css/main.css";
import { router } from "@/router";

createApp(App).use(router).mount("#app");
