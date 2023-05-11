<template>
  <v-sheet color="var(--col-empty)">
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
  </v-sheet>
</template>

<script>
import { myChat } from "@/api/modules/user";
import { getAroundEmojis } from "@/api/modules/emoji";
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      radius: 160,
      angle: 6,
      isClickEmoji: false,
      selectedEmoji: null,
    };
  },
  computed: {
    ...mapState("userStore", ["location", "aroundEmojis"]),
    ...mapState("emojiStore", ["emojiCategory"]),
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
  },
};
</script>

<style>
.spinner {
  z-index: 5;

  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.dot1,
.dot2 {
  width: 4rem;
  -webkit-animation: sk-bounce 2s infinite ease-in-out;
  animation: sk-bounce 2s infinite ease-in-out;
}

.dot2 {
  -webkit-animation-delay: -1s;
  animation-delay: -1s;
}

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
