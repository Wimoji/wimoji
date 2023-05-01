const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    user: {
      id: null, //추후 제거
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
    SET_USER_ID(state, id) {
      state.user.id = id;
    },
    CLEAR_USER(state) {
      state.isLogin = false;
      state.user.nickname = null;
    },
  },
  actions: {
    setLogin({ commit }, user) {
      commit("SET_IS_LOGIN", true);
      commit("SET_USER_NICKNAME", user.nickname);
      commit("SET_USER_ID", user.id);
    },
    setLogout({ commit }) {
      commit("CLEAR_USER");
    },
  },
  modules: {},
};

export default userStore;
