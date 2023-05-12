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
    <!-- <div class="create-emoji">
      <home-page-create-emoji />
    </div> -->
    <!-- <home-emoji></home-emoji> -->
  </v-sheet>
</template>

<script>
import { getAroundEmojis } from "@/api/modules/emoji";
import { mapState, mapActions } from "vuex";
// import HomeEmoji from "@/components/HomePage/HomeEmoji.vue";
// import HomePageCreateEmoji from "@/components/HomePage/HomePageCreateEmoji.vue";
import BlueCircle from "@/common/component/BlueCircle.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
import HomeWhiteCircle from "./HomeWhiteCircle.vue";
// import WhiteCircle from "@/common/component/WhiteCircle.vue";
export default {
  components: {
    // HomePageCreateEmoji,
    // HomeEmoji,
    BlueCircle,
    YellowCircle,
    HomeWhiteCircle,
    // WhiteCircle,
  },
  computed: {
    ...mapState("userStore", ["location", "aroundEmojis"]),
    ...mapState("emojiStore", ["emojiCategory"]),
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
      let result = await getAroundEmojis(this.location);
      if (result == null) {
        console.log("주변 이모지 불러오기 오류 발생");
      } else {
        // console.log("result >> ", result);
        // this.aroundEmojis = result;
        this.setAroundEmojis(result);
      }
      //result가 null이라면 오류, result.length가 0이라면 주변 이모지 없음
    }
  },
  // mounted() {
  //   if (this.location.myPosition == null) {
  //     this.mainPageText = ["위치 권한을 허용해주세요 📍"];
  //   } else {
  //     this.mainPageText = [
  //       `${this.user.nickname}님 안녕하세요😆 \n지금 ${this.location.myPosition}에 있어요`,
  //     ];
  //   }
  // },
  // watch: {
  //   location() {
  //     if (this.location.myPosition == null) {
  //       this.mainPageText = ["위치 권한을 허용해주세요 📍"];
  //     } else {
  //       this.mainPageText = [
  //         `${this.user.nickname}님 안녕하세요😁 \n지금 ${this.location.myPosition}에 있어요`,
  //       ];
  //     }
  //   },
  // },
  methods: {
    ...mapActions("chatStore", ["setNowChatRoom"]),
    ...mapActions("userStore", ["setAroundEmojis"]),
  },
};
</script>
<style>
/* .info-area .resize-white-circle {
  position: absolute;
  top: 50%;
  transform: translate(0, 100%);
} */
/* .info-area .home-white-circle {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  height: 100vh;
} */
.home-area {
  position: relative;
  width: 100%;
  height: 100vh;
}
.home-area .home-blue-circle {
  position: absolute;
  top: 0;
  left: 0;
}
.home-area .home-yellow-circle {
  position: absolute;
  bottom: 0;
  right: 0;
  transform: translate(100%, 100%);
}
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
