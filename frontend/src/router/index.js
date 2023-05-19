import Vue from "vue";
import VueRouter from "vue-router";

import main from "@/router/modules/main";
import my from "@/router/modules/my";
import sign from "@/router/modules/sign";
import error from "@/router/modules/error";

import store from "@/store";

Vue.use(VueRouter);

const routes = [...main, ...my, ...sign, ...error];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  const publicPages = ["/login", "/signup"];
  const isLogin = store.state.userStore.isLogin;

  if (!isLogin && to.path.startsWith("/my")) {
    alert("로그인이 필요한 페이지입니다!");
    next("/login");
  } else if (isLogin && publicPages.includes(to.path)) {
    next("/");
  } else {
    next();
  }
});

export default router;
