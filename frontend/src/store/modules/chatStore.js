const chatStore = {
  namespaced: true,
  state: {
    nowChatRoom: null,
  },
  getters: {},
  mutations: {
    SET_NOW_CHAT_ROOM(state, room) {
      state.nowChatRoom = room;
    },
  },
  actions: {
    setNowChatRoom({ commit }, room) {
      commit("SET_NOW_CHAT_ROOM", room);
    },
  },
  modules: {},
};

export default chatStore;
