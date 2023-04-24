const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN(state, flag) {
      state.isLogin = flag;
    },
  },
  actions: {
    setIsLogin({ commit }, flag) {
      commit("SET_IS_LOGIN", flag);
    },
  },
  modules: {},
};

export default userStore;
