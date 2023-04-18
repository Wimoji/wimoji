import Vue from "vue";
import VueRouter from "vue-router";

import main from "@/router/modules/main";
import home from "@/router/modules/home";
import list from "@/router/modules/list";
import sign from "@/router/modules/sign";
import chat from "@/router/modules/chat";

Vue.use(VueRouter);

const routes = [...main, ...home, ...list, ...sign, ...chat];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;