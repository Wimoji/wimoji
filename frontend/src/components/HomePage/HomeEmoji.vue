<template>
  <v-sheet class="around-emoji-sheet" color="var(--main-col-1)">
    <div class="d-flex pagination-area">
      <div v-for="(idx, i) in totalEmojis.length" :key="i" class="pagination">
        <div v-if="i == 0" class="point selected"><a></a></div>
        <div v-else class="point"><a></a></div>
      </div>
    </div>
    <v-sheet
      color="var(--main-col-1)"
      class="chunk-area"
      v-for="(chunk, idx) in totalEmojis"
      :key="idx"
      :id="'spin' + idx"
      ref="getSpin"
    >
      <v-sheet
        class="spinner"
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
            top: radius * Math.sin(((i + 1) * Math.PI) / angle) + 'px',
            left: radius * Math.cos(((i + 1) * Math.PI) / angle) + 'px',
          }"
        >
        </v-img>

        <v-img
          @click="detailAroundEmoji(item)"
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
    </v-sheet>

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
import { myChat } from "@/api/modules/user";
// import { getAroundEmojis, getEmojis } from "@/api/modules/emoji";
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      radius: 160,
      angle: 5,
      isClickEmoji: false,
      selectedEmoji: null,
      // lines: 0, //이모지 10개 영역 처리
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
        for (let i = 0; i < times; i++) {
          let temp = [];
          for (let j = 0; j < 10; j++) {
            if (this.aroundEmojis[i * 10 + j] == null) {
              break;
            }
            temp.push(this.aroundEmojis[i * 10 + j]);
          }
          this.totalEmojis.push(temp);
        }
        console.log("최종 배열들", this.totalEmojis);
      }
    },
    totalEmojis() {
      if (this.totalEmojis.length > 0) {
        console.log("지금 이모지들", this.totalEmojis.length);
        //요소 중 spin0만 먼저 보이게 하기
        // for (let i = 0; i < this.totalEmojis.length; i++) {
        //   if (i == 0) {
        //     let spin = document.querySelector("#spin0");
        //     console.log("spin", spin);
        //   }
        // }
        // let spin = document.getElementById("spin0");
        console.log("this ref", this.$refs.getSpin);
        // console.log("spin", spin);
      }
    },
  },
  mounted() {
    // console.log("!this ref", this.$refs.spinId);
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
.pagination .point {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin: 5px 5px;
  transition: background-color ease 0.5s;
  cursor: pointer;
}
.pagination .point {
  background: var(--text-col-1);
}
.pagination .point.selected {
  background: var(--main-col-3);
}
.pagination-area {
  position: absolute;
  bottom: 5%;
  left: 50%;
  transform: translateX(-50%);
}
.around-emoji-sheet {
  width: 100%;
  height: 100%;
  overflow: scroll;
}
.detail-emoji {
  z-index: 10;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.spinner {
  z-index: 5;

  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  /* transform: translate(50%, 50%);
  text-align: center; */
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
