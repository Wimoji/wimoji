<template>
  <v-sheet
    class="around-emoji-page"
    color="var(--col-empty)"
    width="90%"
    height="90%"
  >
    <!-- 하얀 원 -->
    <div class="emoji-white-ellipse-container white-ellipse-container">
      <div class="emoji-white-ellipse1 white-ellipse1"></div>
      <div class="emoji-white-ellipse2 white-ellipse2"></div>
    </div>
    <!-- 내 위치 및 이모지 생성 -->
    <div class="home-page-create-emoji">
      <div v-for="(text, i) in mainPageText" :key="i" class="mb-3 rem-font">
        {{ text }}
      </div>
      <home-page-create-emoji class="mt-3"></home-page-create-emoji>
    </div>
    <!-- 내 주변 이모지 -->
    <home-emoji></home-emoji>
  </v-sheet>
</template>

<script>
import HomeEmoji from "@/components/HomePage/HomeEmoji.vue";
import HomePageCreateEmoji from "./HomePageCreateEmoji.vue";
import { mapState } from "vuex";
export default {
  components: {
    HomeEmoji,
    HomePageCreateEmoji,
  },
  data() {
    return {
      mainPageText: null,
    };
  },
  computed: {
    ...mapState("userStore", ["user", "location"]),
  },
  mounted() {
    if (this.location.myPosition == null) {
      this.mainPageText = ["위치 권한을 허용해주세요 📍"];
    } else {
      this.mainPageText = [
        `${this.user.nickname}님 안녕하세요😆`,
        `지금 ${this.location.myPosition}에 있어요`,
      ];
    }
  },
  watch: {
    location() {
      if (this.location.myPosition == null) {
        this.mainPageText = ["위치 권한을 허용해주세요 📍"];
      } else {
        this.mainPageText = [
          `${this.user.nickname}님 안녕하세요😁`,
          `지금 ${this.location.myPosition}에 있어요`,
        ];
      }
    },
  },
};
</script>

<style>
.around-emoji-page {
  position: relative;
}
.around-emoji-page .emoji-white-ellipse-container {
  position: absolute;
  top: 50%;
  left: 50%;
}
.around-emoji-page .emoji-white-ellipse-container .emoji-white-ellipse1 {
  width: 300px;
  height: 305px;
}
.around-emoji-page .emoji-white-ellipse-container .emoji-white-ellipse2 {
  width: 320px;
  height: 325px;
}
.around-emoji-page .home-page-create-emoji {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 85%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
