<template>
  <v-container class="info-area">
    <div class="blue-info">
      <blue-circle></blue-circle>
    </div>
    <div class="yellow-info">
      <yellow-circle></yellow-circle>
    </div>
    <div class="info-item-component create-emoji">
      <home-page-create-emoji />
    </div>
    <!-- <div class="info-item-component">
      <home-emoji></home-emoji>
    </div> -->
    <div class="resize-title-circle"></div>
    <!-- <div class="info-item-component"> -->
    <!-- <spinner-item></spinner-item> -->
    <v-sheet
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
    <!-- </div> -->

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
    </v-card>
  </v-container>
</template>

<script>
import { myChat } from "@/api/modules/user";
import { getAroundEmojis } from "@/api/modules/emoji";
import { mapState, mapActions } from "vuex";
// import HomeEmoji from "@/components/HomePage/HomeEmoji.vue";
import HomePageCreateEmoji from "@/components/HomePage/HomePageCreateEmoji.vue";
import BlueCircle from "@/common/component/BlueCircle.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
// import SpinnerItem from "@/components/HomePage/SpinnerItem.vue";
// import Scene from "scenejs";
export default {
  components: {
    HomePageCreateEmoji,
    // HomeEmoji,
    BlueCircle,
    YellowCircle,
    // SpinnerItem,
  },
  computed: {
    ...mapState("userStore", ["location", "aroundEmojis"]),
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  data() {
    return {
      radius: 165,
      angle: 6,
      isClickEmoji: false,
      selectedEmoji: null,
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
  methods: {
    ...mapActions("chatStore", ["setNowChatRoom"]),
    ...mapActions("userStore", ["setAroundEmojis"]),
    detailAroundEmoji(index) {
      //사용자의 상세 이모지 보기
      this.isClickEmoji = true;
      this.selectedEmoji = this.aroundEmojis[index];
    },
    async joinChat() {
      //지금 선택된 이모지의 채팅방 참여하기
      this.setNowChatRoom(this.selectedEmoji);
      const params = {
        rid: this.selectedEmoji.rid,
      };
      await myChat(
        params,
        ({ data }) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
      this.$router.push({
        name: "chatting",
        params: { roomId: this.selectedEmoji.rid, data: this.selectedEmoji },
      });
    },
    closeEmoji() {
      this.isClickEmoji = false;
    },
  },
};
</script>
<style scoped>
.create-emoji {
  z-index: 6;
}
.detail-emoji {
  z-index: 5;
  position: fixed;
  top: 50%;
  left: 50%;
  max-width: 500px;
  transform: translate(-50%, -50%);
  width: 80%;
}
.info-area {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}
.blue-info {
  position: absolute;
  bottom: 50%;
  right: 50%;
  scale: 0.7;
  z-index: 1;
}
.yellow-info {
  position: absolute;
  top: 50%;
  left: 50%;
  scale: 0.8;
  z-index: 1;
}
.info-item-component {
  top: 50%;
  left: 50%;
  text-align: center;
  transform: translate(-50%, -50%);
  z-index: 2;
}
.resize-title-circle {
  z-index: 1;
  box-sizing: border-box;

  position: fixed;
  width: 50%;
  height: 50%;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  border-radius: 50%;
  animation: rotate1 8s linear infinite;

  width: 320px;
  height: 310px;
  animation: rotate2 4s linear infinite;
  border: 2px solid #ffffff;
  background: rgba(255, 255, 255, 0.31);
}
.spinner {
  /*margin-left: -20px;*/
  /* margin-top: -20px; */
  z-index: 5;

  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  /* -webkit-animation: sk-rotate 2.0s infinite linear; */
  /* animation: sk-rotate 2.0s infinite linear; */
}

.dot1,
.dot2 {
  /* position: absolute; */
  /* display: inline-block; */
  width: 4rem;
  -webkit-animation: sk-bounce 2s infinite ease-in-out;
  animation: sk-bounce 2s infinite ease-in-out;
}

.dot2 {
  -webkit-animation-delay: -1s;
  animation-delay: -1s;
}

/* @-webkit-keyframes sk-rotate { 100% { -webkit-transform: rotate(360deg) }}
@keyframes sk-rotate { 100% { transform: rotate(360deg); -webkit-transform: rotate(360deg) }} */

@-webkit-keyframes sk-bounce {
  0%,
  100% {
    -webkit-transform: scale(0.7);
  }
  50% {
    -webkit-transform: scale(1);
  }
}

@keyframes sk-bounce {
  0%,
  100% {
    transform: scale(0.7);
    -webkit-transform: scale(0.7);
  }
  50% {
    transform: scale(1);
    -webkit-transform: scale(1);
  }
}
</style>
