import Vue from "vue";
import VueRouter from "vue-router";

import main from "@/router/modules/main";
import list from "@/router/modules/list";
import sign from "@/router/modules/sign";

import store from "@/store";

Vue.use(VueRouter);

const routes = [...main, ...list, ...sign];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  //세션에 토큰 있는지 확인
  const token = sessionStorage.getItem("access-token");
  if (token) {
    store.dispatch("userStore/setIsLogin", true);
  }
  var isLogin = store.state.userStore.isLogin;
  console.log("이동해용", isLogin);
  next();
});

export default router;
