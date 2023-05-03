const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    user: {
      id: null, //추후 제거
      nickname: null,
    },
    // location: {
    //   latitude: "37.5013488",
    //   longitude: "127.0397167",
    //   dongCode: "1168010800",
    // },
    location: {
      latitude: null,
      longitude: null,
      dongCode: null,
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
    SET_LOCATION(state, location) {
      state.location = location;
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
    setLocation({ commit }, location) {
      commit("SET_LOCATION", location);
    },
    setLogout({ commit }) {
      commit("CLEAR_USER");
    },
  },
  modules: {},
};

export default userStore;
