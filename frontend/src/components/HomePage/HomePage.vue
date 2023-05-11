<template>
  <v-container class="info-area home-area">
    <div class="info-blue-circle">
      <blue-circle></blue-circle>
    </div>
    <div class="info-yellow-circle">
      <yellow-circle></yellow-circle>
    </div>
    <div class="resize-white-circle">
      <white-circle :propsText="mainPageText"></white-circle>
    </div>
    <div class="create-emoji">
      <home-page-create-emoji />
    </div>
    <home-emoji></home-emoji>
    <!-- <div class="around-emoji">
      <home-emoji></home-emoji>
    </div> -->
    <!-- <div class="resize-title-circle"></div> -->
    <!-- <div class="info-item-component"> -->
    <!-- <spinner-item></spinner-item> -->

    <!-- <v-sheet
      class="spinner"
      color="var(--col-empty)"
      v-for="(item, i) in aroundEmojis"
      :key="i"
    >
      <v-img
        @click="detailAroundEmoji(i)"
        v-if="i % 2 == 0"
        class="dot1"
        :src="emojiCategory[item.eid].link"
        :style="{
          top: radius * Math.sin(((i + 1) * Math.PI) / angle) + 'px',
          left: radius * Math.cos(((i + 1) * Math.PI) / angle) + 'px',
        }"
      >
      </v-img>

      <v-img
        @click="detailAroundEmoji(i)"
        v-else
        class="dot2"
        :src="emojiCategory[item.eid].link"
        :style="{
          top: radius * Math.sin(((i + 1) * Math.PI) / angle) + 'px',
          left: radius * Math.cos(((i + 1) * Math.PI) / angle) + 'px',
        }"
      >
      </v-img>
    </v-sheet>

    <v-card
      v-if="isClickEmoji"
      class="detail-emoji d-flex flex-column align-center pa-5"
    >
      <v-img width="30%" :src="emojiCategory[selectedEmoji.eid].link"></v-img>
      <v-card-title>{{ selectedEmoji.title }}</v-card-title>
      <v-row class="d-flex">
        <v-col>
          <v-btn
            height="3em"
            width="6em"
            rounded
            color="var(--main-col-3)"
            class="white-col-1"
            @click="joinChat"
            >함께하기</v-btn
          >
        </v-col>
        <v-col>
          <v-btn
            height="3em"
            width="6em"
            rounded
            color="var(--text-col-4)"
            class="white-col-1"
            @click="closeEmoji"
            >닫기</v-btn
          >
        </v-col>
      </v-row>
    </v-card> -->
  </v-container>
</template>

<script>
import { getAroundEmojis } from "@/api/modules/emoji";
import { mapState, mapActions } from "vuex";
import HomeEmoji from "@/components/HomePage/HomeEmoji.vue";
import HomePageCreateEmoji from "@/components/HomePage/HomePageCreateEmoji.vue";
import BlueCircle from "@/common/component/BlueCircle.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
import WhiteCircle from "@/common/component/WhiteCircle.vue";
export default {
  components: {
    HomePageCreateEmoji,
    HomeEmoji,
    BlueCircle,
    YellowCircle,
    WhiteCircle,
  },
  computed: {
    ...mapState("userStore", ["location", "aroundEmojis"]),
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  data() {
    return {
      isClickEmoji: false,
      selectedEmoji: null,
      mainPageText: null,
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
  mounted() {
    if (this.location.myPosition == null) {
      this.mainPageText = ["위치 권한을 허용해주세요 📍"];
    } else {
      this.mainPageText = [`지금 나는 ${this.location.myPosition}에 있어요`];
    }
  },
  watch: {
    location() {
      if (this.location.myPosition == null) {
        this.mainPageText = ["위치 권한을 허용해주세요 📍"];
      } else {
        this.mainPageText = [`지금 나는 ${this.location.myPosition}에 있어요`];
      }
    },
  },
  methods: {
    ...mapActions("chatStore", ["setNowChatRoom"]),
    ...mapActions("userStore", ["setAroundEmojis"]),
  },
};
</script>
<style>
.info-area .resize-white-circle {
  position: absolute;
  top: 50%;
  transform: translate(0, 100%);
}
/* .info-area .resize-white-circle {
  position: absolute;
  top: 50%;
} */
.home-area {
  position: relative;
}
.home-area .create-emoji {
  position: relative;
  width: 100%;
  height: 100%;
}
</style>
