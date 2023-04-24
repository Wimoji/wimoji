import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
  created() {
    //새로고침 될 때 세션 스토리지에 토큰이 있다면 로그인 상태로 변경합니다
    const token = sessionStorage.getItem("access-token");
    if (token) {
      this.$store.dispatch("userStore/setIsLogin", true);
    }
  },
}).$mount("#app");
