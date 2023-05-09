const chatStore = {
  namespaced: true,
  state: {
    isDelete: false,
    nowChatRoom: null,
  },
  getters: {},
  mutations: {
    SET_IS_CHAT(state, flag) {
      state.isDelete = flag;
    },
    SET_NOW_CHAT_ROOM(state, room) {
      state.nowChatRoom = room;
    },
  },
  actions: {
    setIsDelete({ commit }, flag) {
      commit("SET_IS_CHAT", flag);
    },
    setNowChatRoom({ commit }, room) {
      commit("SET_NOW_CHAT_ROOM", room);
    },
  },
  modules: {},
};

export default chatStore;
