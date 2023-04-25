import Vue from "vue";
import Vuex from "vuex";

import userStore from "@/store/modules/userStore.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    userStore: userStore,
  },
});
