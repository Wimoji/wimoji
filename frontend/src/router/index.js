import Vue from "vue";
import VueRouter from "vue-router";

import main from "@/router/modules/main";
import home from "@/router/modules/home";
import list from "@/router/modules/list";
import sign from "@/router/modules/sign";

Vue.use(VueRouter);

const routes = [...main, ...home, ...list, ...sign];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;