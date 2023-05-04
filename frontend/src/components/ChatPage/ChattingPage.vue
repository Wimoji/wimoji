<template>
  <v-container>
    <v-sheet ref="chatArea" class="chat-area col-transparent">
      <v-row dense class="mx-1" v-for="message in messages" :key="message.id">
        <!-- 나의 메시지 -->
        <v-col class="d-flex flex-column align-end" v-if="message.flag == 1">
          <div class="main-font-bd xs-font pr-1">{{ message.nickname }}</div>
          <v-chip
            class="xs-font pa-4"
            text-color="white"
            color="var(--main-col-3)"
          >
            {{ message.content }}
          </v-chip>
        </v-col>
        <!-- 남의 메시지 -->
        <v-col v-else-if="message.flag == 2">
          <div class="main-font-bd xs-font pl-1">{{ message.nickname }}</div>
          <v-chip class="xs-font pa-4" color="white">{{
            message.content
          }}</v-chip>
        </v-col>
        <!-- 입퇴장 -->
        <v-col v-else class="text-col-1 xs-font text-center my-3">
          {{ message.content }}
        </v-col>
      </v-row>
    </v-sheet>
    <v-form class="send-area">
      <v-text-field
        class="chat-text-field pr-3"
        hide-details
        rounded
        solo
        background-color="white"
        v-model="content"
        @keyup.enter="sendMessage"
      >
        <template v-slot:label>
          <div class="xs-font text-col3">내용을 입력해주세요</div>
        </template>
      </v-text-field>
      <v-btn
        v-if="content != null"
        dark
        color="var(--main-col-3)"
        class="send-btn"
        fab
        small
        @click="sendMessage"
      >
        <v-icon>mdi-arrow-up</v-icon>
      </v-btn>
      <v-btn
        v-else
        color="var(--main-col-3)"
        class="send-btn"
        fab
        small
        @click="sendMessage"
        disabled
      >
        <v-icon>mdi-arrow-up</v-icon>
      </v-btn>
    </v-form>

    <!-- <div>
      <button @click="exitWebSocket">채팅방 완전 나가기</button>
    </div> -->
  </v-container>
</template>

<script>
import StompJS from "stompjs";
import SockJS from "sockjs-client";
import { mapState } from "vuex";
import {
  getLastReadIdx,
  getNewChatMessage,
  saveLastMessage,
} from "@/api/modules/websocket";

export default {
  data() {
    return {
      serverURL: `${process.env.VUE_APP_API_SERVICE_URL}/chat-service`,

      messages: [],
      room: [],
      content: null,
      lastReadIdx: "",
      socket: null,
      lastActiveTime: null,
    };
  },
  watch: {
    messages() {
      //화면에 추가되었을 때 스크롤 처리
      this.$nextTick(() => {
        const chatArea = document.querySelector(".chat-area");
        chatArea.scrollTo(0, chatArea.scrollHeight);
      });
    },
  },
  computed: {
    ...mapState("chatStore", ["nowChatRoom"]),
  },
  async created() {
    this.room = this.nowChatRoom;
    console.log("이 방의 rid >>> ", this.room.rid);
    console.log("이 방의 id >>> ", this.room.id);

    this.lastActiveTime = Date.now();
    setInterval(() => {
      this.checkUserActivity();
    }, 300000);

    //마지막 메시지 조회
    this.lastReadIdx = await getLastReadIdx(this.room.id);
    if (this.lastReadIdx == null) {
      alert("마지막 메시지 조회 실패");
    } else if (this.lastReadIdx == 0) {
      this.connect();
    } else {
      this.getNewMessage(this.lastReadIdx);
    }
  },
  mounted() {
    window.addEventListener("beforeprint", this.disconnectWebSocket);
    // 스크롤 처리
    const chatArea = document.querySelector(".chat-area");
    chatArea.scrollTo(0, chatArea.scrollHeight);
  },
  beforeUnmount() {
    window.removeEventListener("beforeunload", this.disconnectWebSocket);
  },
  methods: {
    async getLastReadChatIdx() {
      this.lastReadIdx = await getLastReadIdx(this.room.id);
      if (this.lastReadIdx == null) alert("읽는중 에러 발생");
    },
    connect() {
      const sockJs = new SockJS(`${this.serverURL}/ws/chat`);
      this.socket = StompJS.over(sockJs);

      // console.log("header>>>>", api.headers);
      let token = sessionStorage.getItem("access-token");

      const headers = {
        Authorization: token,
        rid: this.room.id,
      };

      this.socket.connect(
        headers,
        (frame) => {
          console.log("소켓 연결 성공: ", frame);
          this.socket.send(
            "/pub/chat/enter",
            { Authorization: token },
            { rid: this.room.id }
          );

          this.socket.subscribe(`/sub/chat/${this.room.id}`, (msg) => {
            this.messages.push(JSON.parse(msg.body));
          }),
            (error) => {
              console.log("메시지 수신 실패 ", error);
            };
        },
        (error) => {
          console.log("소켓 연결 실패 ", error);
          // rid 오류, uid 오류, 인원 최대
          if (error.command === "ERROR") {
            alert("채팅방에 접속할 수 없습니다.");
            this.$router.push("/my/chat");
          }
        }
      );
    },
    sendMessage() {
      //임시로 붙이기
      // this.messages.push({
      //   rid: "6ab2d3ee3edf",
      //   nickname: "테스트!",
      //   content: this.content,
      //   flag: 1,
      // });

      let token = sessionStorage.getItem("access-token");

      if (this.socket && this.socket.connected) {
        const msg = {
          rid: this.room.id,
          content: this.content,
        };
        this.socket.send(
          "/pub/chat/message",
          { Authorization: token },
          JSON.stringify(msg)
        );
      }
      this.content = null;
      this.lastActiveTime = Date.now();
    },
    async getNewMessage(idx) {
      var result = await getNewChatMessage({ id: this.room.id, idx: idx });
      this.messages.push(result);
    },
    checkUserActivity() {
      const timeDiff = Date.now() - this.lastActiveTime;
      // 5분 동안 활동이 없으면 WebSocket disconnect
      if (timeDiff > 300000) {
        this.disconnectWebSocket();
      }
    },
    disconnectWebSocket() {
      // 마지막 메시지 저장
      saveLastMessage(this.room.id);

      this.socket.disconnect(() => {
        console.log("WebSocket 연결 종료");
        this.$router.push("/my/chat");
      });
    },
    exitWebSocket() {
      this.socket.send(
        "/pub/chat/exit",
        { Authorization: sessionStorage.getItem("access-token") },
        { rid: this.room.id }
      );
    },
  },
};
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
}
.chat-area {
  height: 90%;
  overflow: scroll;
  padding-top: 85px;
}
.send-area {
  height: 10%;
  bottom: 0;

  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: var(--main-col-1);
}
</style>
