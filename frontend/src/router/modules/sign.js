import LoginPage from "@/components/SignPage/LoginPage.vue";
import SignupPage from "@/components/SignPage/SignupPage.vue";

import store from "@/store";

const sign = [
  {
    path: "/login",
    name: "login",
    components: {
      default: LoginPage,
    },
    beforeEnter: (to, from, next) => {
      if (store.state.userStore.isLogin) {
        //로그인 한 상태라면 메인 화면으로 리다이렉트
        next("/");
      } else {
        next();
      }
    },
  },
  {
    path: "/signup",
    name: "signup",
    components: {
      default: SignupPage,
    },
    beforeEnter: (to, from, next) => {
      if (store.state.userStore.isLogin) {
        //로그인 한 상태라면 메인 화면으로 리다이렉트
        next("/");
      } else {
        next();
      }
    },
  },
];

export default sign;
