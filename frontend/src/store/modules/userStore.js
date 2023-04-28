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
    CLEAR_USER(state) {
      state.isLogin = false;
      state.user.nickname = null;
    },
  },
  actions: {
    setLogin({ commit }, nickname) {
      commit("SET_IS_LOGIN", true);
      commit("SET_USER_NICKNAME", nickname);
    },
    setLogout({ commit }) {
      commit("CLEAR_USER");
    },
  },
  modules: {},
};

export default userStore;
