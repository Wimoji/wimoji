<template>
  <v-sheet color="var(--col-empty)" class="home-area">
    <div class="home-blue-circle">
      <blue-circle></blue-circle>
    </div>
    <div class="home-yellow-circle">
      <yellow-circle></yellow-circle>
    </div>
    <div class="home-center-area">
      <home-white-circle></home-white-circle>
    </div>
  </v-sheet>
</template>

<script>
import { getAroundEmojis, getEmojis } from "@/api/modules/emoji";
import { mapState, mapActions } from "vuex";
import BlueCircle from "@/common/component/BlueCircle.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
import HomeWhiteCircle from "./HomeWhiteCircle.vue";
export default {
  components: {
    BlueCircle,
    YellowCircle,
    HomeWhiteCircle,
  },
  computed: {
    ...mapState("userStore", ["location"]),
    ...mapState("emojiStore", ["emojiCategory", "aroundEmojis"]),
  },
  data() {
    return {
      isClickEmoji: false,
      selectedEmoji: null,
      // mainPageText: null,
    };
  },
  async created() {
    //지금 dongcode로 주변 사용자의 이모지 불러오기
    if (this.location.dongCode != null) {
      let resultAround = await getAroundEmojis(this.location);
      let resultMyEmoji = await getEmojis();
      //내 주변 이모지 설정
      this.setAroundEmojis(resultAround);
      //나의 이모지 추가
      this.addMyEmojisToAroundEmojis(resultMyEmoji);
    }
  },
  methods: {
    ...mapActions("chatStore", ["setNowChatRoom"]),
    ...mapActions("emojiStore", [
      "setAroundEmojis",
      "addMyEmojisToAroundEmojis",
    ]),
  },
};
</script>
<style>
.home-area .home-center-area {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
}
.create-emoji {
  position: fixed;
  top: 0;

  z-index: 100;
}
/* .info-area .resize-white-circle {
  position: absolute;
  top: 50%;
} */
/* .home-area {
  position: relative;
} */
/* .home-area .create-emoji {
  position: relative;
  width: 100%;
  height: 100%;
} */
</style>
