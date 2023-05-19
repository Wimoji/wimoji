const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    // isLogin: true,
    user: {
      id: null, //추후 제거
      nickname: null,
    },
    location: {
      latitude: null,
      longitude: null,
      dongCode: null,
      myPosition: null,
    },
    // aroundEmojis: [],
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
      state.user = null;
      state.location = null;
    },
    // SET_AROUND_EMOJIS(state, emojis) {
    //   state.aroundEmojis = emojis;
    // },
    // CLEAR_AROUND_EMOJIS(state) {
    //   state.aroundEmojis = [];
    // },
    // ADD_MY_EMOJIS_TO_AROUND_EMOJIS(state, emojis) {
    //   state.aroundEmojis.push(...emojis);
    // },
  },
  actions: {
    setLogin({ commit }, user) {
      commit("SET_IS_LOGIN", true);
      commit("SET_USER_NICKNAME", user.nickname);
      commit("SET_USER_ID", user.id);
    },
    setLocation({ commit }, location) {
      commit("SET_LOCATION", location);
    },
    setLogout({ commit }) {
      commit("CLEAR_USER");
    },
    // setAroundEmojis({ commit }, emojis) {
    //   commit("SET_AROUND_EMOJIS", emojis);
    // },
    // clearAroundEmojis({ commit }) {
    //   commit("CLEAR_AROUND_EMOJIS");
    // },
    // addMyEmojisToAroundEmojis({ commit }, emojis) {
    //   commit("ADD_MY_EMOJIS_TO_AROUND_EMOJIS", emojis);
    // },
  },
  modules: {},
};

export default userStore;
