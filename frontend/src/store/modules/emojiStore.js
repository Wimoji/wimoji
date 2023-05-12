const emojiStore = {
  namespaced: true,
  state: {
    aroundEmojis: [],
    myPageView: null,
    emojiCategory: [
      {
        id: 0,
        title: "운동",
        link: "https://api.iconify.design/fluent-emoji:person-running.svg",
      },
      {
        id: 1,
        title: "여행",
        link: "https://api.iconify.design/fluent-emoji:airplane.svg",
      },
      {
        id: 2,
        title: "밥",
        link: "https://api.iconify.design/fluent-emoji:cooked-rice.svg",
      },
      {
        id: 3,
        title: "배달",
        link: "https://api.iconify.design/fluent-emoji:motor-scooter.svg",
      },
      {
        id: 4,
        title: "전화",
        link: "https://api.iconify.design/fluent-emoji:telephone-receiver.svg",
      },
      {
        id: 5,
        title: "산책",
        link: "https://api.iconify.design/fluent-emoji:service-dog.svg",
      },
      {
        id: 6,
        title: "영화",
        link: "https://api.iconify.design/fluent-emoji:movie-camera.svg",
      },
      {
        id: 7,
        title: "도와줘!",
        link: "https://api.iconify.design/fluent-emoji:person-bowing.svg",
      },
      {
        id: 8,
        title: "알바",
        link: "https://api.iconify.design/fluent-emoji:man-juggling.svg",
      },
      {
        id: 9,
        title: "벌레",
        link: "https://api.iconify.design/fluent-emoji:ant.svg",
      },
      {
        id: 10,
        title: "취미",
        link: "https://api.iconify.design/fluent-emoji:woman-dancing.svg",
      },
      {
        id: 11,
        title: "육아",
        link: "https://api.iconify.design/fluent-emoji:person-feeding-baby.svg",
      },
      {
        id: 12,
        title: "채팅",
        link: "https://api.iconify.design/fluent-emoji:left-speech-bubble.svg",
      },
      {
        id: 13,
        title: "게임",
        link: "https://api.iconify.design/fluent-emoji:joystick.svg",
      },
      {
        id: 14,
        title: "유흥",
        link: "https://api.iconify.design/fluent-emoji:beer-mug.svg",
      },
    ],
  },
  getters: {},
  mutations: {
    SET_MY_PAGE_VIEW(state, myPageView) {
      state.myPageView = myPageView;
    },
    SET_AROUND_EMOJIS(state, emojis) {
      state.aroundEmojis = emojis;
    },
    CLEAR_AROUND_EMOJIS(state) {
      state.aroundEmojis = [];
    },
    ADD_MY_EMOJIS_TO_AROUND_EMOJIS(state, emojis) {
      state.aroundEmojis.push(...emojis);
    },
  },
  actions: {
    setMyPageView({ commit }, myPageView) {
      commit("SET_MY_PAGE_VIEW", myPageView);
    },
    setAroundEmojis({ commit }, emojis) {
      commit("SET_AROUND_EMOJIS", emojis);
    },
    clearAroundEmojis({ commit }) {
      commit("CLEAR_AROUND_EMOJIS");
    },
    addMyEmojisToAroundEmojis({ commit }, emojis) {
      commit("ADD_MY_EMOJIS_TO_AROUND_EMOJIS", emojis);
    },
  },
  modules: {},
};

export default emojiStore;
