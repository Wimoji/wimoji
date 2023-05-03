import Vue from "vue";
import Vuex from "vuex";

import createPersistedState from "vuex-persistedstate";

import userStore from "@/store/modules/userStore.js";
import emojiStore from "@/store/modules/emojiStore.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    userStore: userStore,
    emojiStore: emojiStore,
  },
  plugins: [
    //새로고침시 로그인 상태 유지
    createPersistedState({
      storage: window.sessionStorage,
      paths: ["userStore"],
    }),
  ],
});

