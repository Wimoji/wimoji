<template>
  <v-sheet class="around-emoji-sheet" color="var(--com-empty)">
    <Flicking
      class="home-flicking"
      :options="{ gap: 30, horizontal: true }"
      :plugins="plugins"
    >
      <!-- 10개씩 분리 -->
      <v-sheet
        class="chunk-area"
        color="var(--main-col-4)"
        v-for="(chunk, idx) in totalEmojis"
        :key="idx"
      >
        <!-- 분리된 최대 10개의 이모지 -->
        <v-sheet
          class="inner-emojis"
          color="var(--col-empty)"
          v-for="(item, i) in chunk"
          :key="i"
        >
          <v-img
            @click="detailAroundEmoji(item)"
            v-if="i % 2 == 0"
            class="dot1"
            :src="emojiCategory[item.eid].link"
            :style="{
              top:
                radius * Math.sin(((i + 1) * Math.PI) / (chunk.length / 2)) +
                'px',
              left:
                radius * Math.cos(((i + 1) * Math.PI) / (chunk.length / 2)) +
                'px',
            }"
          ></v-img>
          <v-img
            @click="detailAroundEmoji(item)"
            v-else
            class="dot2"
            :src="emojiCategory[item.eid].link"
            :style="{
              top:
                radius * Math.sin(((i + 1) * Math.PI) / (chunk.length / 2)) +
                'px',
              left:
                radius * Math.cos(((i + 1) * Math.PI) / (chunk.length / 2)) +
                'px',
            }"
          ></v-img>
        </v-sheet>
      </v-sheet>
      <div slot="viewport" class="flicking-pagination"></div>
    </Flicking>
    <!-- 이모지 상세 보기 -->
    <v-card
      width="85%"
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
import { Flicking } from "@egjs/vue-flicking";
import { Pagination } from "@egjs/flicking-plugins";
import "@egjs/flicking-plugins/dist/pagination.css";

import { myChat } from "@/api/modules/user";
import { mapState, mapActions } from "vuex";
export default {
  components: { Flicking },
  data() {
    return {
      radius: 160,
      plugins: [new Pagination({ type: "bullet" })],
      isClickEmoji: false,
      selectedEmoji: null,
      totalEmojis: [],
    };
  },
  computed: {
    ...mapState("userStore", ["location"]),
    ...mapState("emojiStore", ["emojiCategory", "aroundEmojis"]),
  },
  watch: {
    aroundEmojis() {
      if (this.aroundEmojis.length > 0) {
        //totalEmojis에 10개씩 잘라서 넣기
        let times = this.aroundEmojis.length / 10;
        //이모지 합치기
        for (let i = 0; i < times; i++) {
          let temp = [];
          for (let j = 0; j < 10; j++) {
            if (this.aroundEmojis[i * 10 + j] == null) {
              break;
            }
            temp.push(this.aroundEmojis[i * 10 + j]);
          }
          this.totalEmojis.push(temp); //각도 인덱스와 이모지 저장
        }
        // console.log("최종 배열들", this.totalEmojis);
      }
    },
  },

  methods: {
    ...mapActions("chatStore", ["setNowChatRoom", "clearNowChatRoom"]),
    ...mapActions("emojiStore", [
      "setAroundEmojis",
      "addMyEmojisToAroundEmojis",
    ]),
    detailAroundEmoji(item) {
      //사용자의 상세 이모지 보기
      this.isClickEmoji = true;
      this.selectedEmoji = item;
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
          this.$router.push("/my/chat");
        },
        (error) => {
          console.log(error);
          alert("채팅방 인원이 마감됐어요!.");
          this.clearNowChatRoom();
          this.$router.push("/");
        }
      );
    },
    closeEmoji() {
      this.isClickEmoji = false;
    },
  },
};
</script>

<style>
.around-emoji-sheet {
  width: 100%;
  height: 100%;
  position: relative;
}
.home-flicking {
  position: relative;
  top: 50%;
}
.chunk-area {
  position: relative;
  width: 100%;
  height: 100%;
  margin-right: 100%;
  animation: rotate_image 35s linear infinite;
  transform-origin: 50% 50%;
}
.inner-emojis {
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
}
.inner-emojis .v-image {
  width: 3rem;
}
.dot1 {
  animation: rotate_image_reverse1 35s ease-in-out infinite;
}

.dot2 {
  animation: rotate_image_reverse2 35s ease-in-out infinite;
}
.detail-emoji {
  z-index: 10;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>
