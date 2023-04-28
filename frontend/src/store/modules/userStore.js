const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    user: {
      nickname: null,
    },
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN(state, flag) {
      state.isLogin = flag;
    },
    SET_USER_NICKNAME(state, nickname) {
      state.user.nickname = nickname;
    },
  },
  actions: {
    setLogin({ commit }, nickname) {
      commit("SET_IS_LOGIN", true);
      commit("SET_USER_NICKNAME", nickname);
    },
  },
  modules: {},
};

export default userStore;
