const userStore = {
  state: {
    isLogin: false,
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN(state, flag) {
      state.isLogin = flag;
    },
  },
  actions: {},
  modules: {},
};

export default userStore;
