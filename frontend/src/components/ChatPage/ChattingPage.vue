<template>
  <v-container>
    <v-sheet ref="chatArea" class="chat-area col-transparent">
      <!-- <infinite-loading
        @infinite="infiniteHandler"
        direction="top"
      ></infinite-loading> -->
      <v-row dense class="mx-1" v-for="message in messages" :key="message.id">
        <!-- 나의 메시지 -->
        <v-col
          class="d-flex flex-column align-end"
          v-if="
            message.flag == 1 ||
            (message.flag != 1 &&
              message.flag != 2 &&
              message.flag != 3 &&
              message.flag == mySessionId)
          "
        >
          <!-- <v-col class="d-flex flex-column align-end"> -->
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
        <v-col
          v-else-if="
            message.flag == 2 ||
            (message.flag != 1 &&
              message.flag != 2 &&
              message.flag != 3 &&
              message.flag != mySessionId)
          "
        >
          <div class="main-font-bd xs-font pl-1">{{ message.nickname }}</div>
          <v-chip class="xs-font pa-4" color="white">{{
            message.content
          }}</v-chip>
        </v-col>
        <!-- 입퇴장 -->
        <v-col
          v-else-if="message.flag == 3"
          class="text-col-1 xs-font text-center my-3"
        >
          {{ message.content }}
        </v-col>
        <!-- 1,2,3이 아닌 경우 : session id를 받음 -->
        <!-- <v-col v-else class="text-col-1 xs-font text-center my-3">
          {{ message.content }}
        </v-col> -->
      </v-row>
    </v-sheet>
    <v-form class="send-area">
      <input type="text" style="display: none" />
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
import { mapState, mapActions } from "vuex";
import {
  getLastReadIdx,
  getNewChatMessage,
  saveLastMessage,
} from "@/api/modules/websocket";
import { getPrevChat } from "@/api/modules/chat";
// import InfiniteLoading from "vue-infinite-loading";

export default {
  // components: { InfiniteLoading },
  data() {
    return {
      serverURL: `${process.env.VUE_APP_API_SERVICE_URL}/chat-service`,
      messages: [],
      room: [],
      content: null,
      lastReadIdx: "",
      socket: null,
      lastActiveTime: null,
      mySessionId: null,
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
    isDelete() {
      if (this.isDelete) {
        //채팅방 삭제하기 버튼을 눌렀을 경우
        //채팅방 삭제
        this.exitWebSocket();
        //isDelete -> false 변경
        this.setIsDelete(false);
      }
    },
  },
  computed: {
    ...mapState("chatStore", ["nowChatRoom", "isDelete"]),
  },
  async created() {
    this.room = this.nowChatRoom;
    console.log("이 방의 정보", this.room);
    console.log("이 방의 rid >>> ", this.room.rid);
    console.log("이 방의 id >>> ", this.room.id);

    this.lastActiveTime = Date.now();
    setInterval(() => {
      this.checkUserActivity();
    }, 300000);

    //마지막 메시지 조회
    this.lastReadIdx = await getLastReadIdx(this.room.id);
    console.log("마지막 메시지 인덱스 axios 이후>>", this.lastReadIdx);
    if (this.lastReadIdx == null) {
      alert("마지막 메시지 조회 실패");
    } else if (this.lastReadIdx == 0) {
      this.connect();
    } else {
      this.getNewMessage(this.lastReadIdx);
      this.connect();
    }
  },
  mounted() {
    // alert("마운트 됨 이벤트 등록");
    // window.addEventListener("beforeunload", this.disconnectWebSocket);
    // 스크롤 처리
    const chatArea = document.querySelector(".chat-area");
    chatArea.scrollTo(0, chatArea.scrollHeight);

    //session id 설정
    // this.mySessionId = sessionStorage.
  },
  beforeDestroy() {
    alert("창끔");
    this.disconnectWebSocket();
    // window.removeEventListener("beforeunload", this.disconnectWebSocket);
  },
  methods: {
    ...mapActions("chatStore", ["setIsDelete"]),
    async infiniteHandler($state) {
      //axios 요청
      const params = {
        rid: "",
        idx: "",
      };
      await getPrevChat(params, ({ data }) => {
        console.log("이전 채팅 데이터를 불러왔어요", data);
        var result = data.data;
        if (result != null) {
          // this.messages앞에 데이터 push
          this.messages.unshift(result);
          $state.loaded();
        } else {
          $state.complete();
        }
      });
    },
    async getLastReadChatIdx() {
      this.lastReadIdx = await getLastReadIdx(this.room.id);
      if (this.lastReadIdx == null) alert("읽는중 에러 발생");
    },
    connect() {
      const sockJs = new SockJS(`${this.serverURL}/ws/chat`);
      this.socket = StompJS.over(sockJs);

      // console.log("header>>>>", api.headers);
      let token = "Bearer " + sessionStorage.getItem("access-token");

      const headers = {
        Authorization: token,
        rid: this.room.id,
      };

      this.socket.connect(
        headers,
        (frame) => {
          console.log("소켓 연결 성공: ", frame);
          console.log("아이디달라고 ", sockJs._transport.url);
          const url = sockJs._transport.url;
          const regex = /\/(\w+)\/websocket$/;
          const sessionId = url.match(regex)[1];
          console.log("정규식햇덩~", sessionId); // n2dzsmev
          this.mySessionId = sessionId;

          this.socket.send(
            "/pub/chat/enter",
            { Authorization: token },
            JSON.stringify(this.room.id)
          );
          // this.socket.send(
          //   "/pub/chat/enter",
          //   { Authorization: token },
          //   { rid: this.room.id }
          // );
          console.log("이 사이에 호출");

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

      let token = "Bearer " + sessionStorage.getItem("access-token");

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
      console.log("읽지않은채팅 함수후에", result);
      result.forEach((el) => {
        this.messages.push({
          nickname: el.nickname,
          flag: el.flag,
          content: el.content,
        });
      });
      // this.messages.push(result);
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

      // this.socket.disconnect(() => {
      //   console.log("WebSocket 연결 종료");
      //   this.$router.push("/my/chat");
      // });
      this.socket.disconnect(() => {});
      console.log("WebSocket 연결 종료");
      // this.$router.push("/my/chat");
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
