<template>
  <v-sheet color="var(--col-empty)" class="chat-room-container">
    <v-sheet ref="chatArea" class="chat-area col-transparent">
      <infinite-loading @infinite="infiniteHandler" direction="top">
        <div slot="no-results"></div>
        <div slot="no-more"></div>
      </infinite-loading>
      <v-row dense class="mx-1" v-for="(message, i) in messages" :key="i">
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
          <div class="main-font-bd xs-font pr-1">{{ message.nickname }}</div>
          <div class="d-flex align-end">
            <div class="xxxs-font mr-1">{{ message.mtime }}</div>
            <v-chip
              class="xs-font pa-4"
              text-color="white"
              color="var(--main-col-3)"
            >
              {{ message.content }}
            </v-chip>
          </div>
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
          <div class="d-flex align-end">
            <v-chip class="xs-font pa-4" color="white">{{
              message.content
            }}</v-chip>
            <div class="xxxs-font ml-1">{{ message.mtime }}</div>
          </div>
        </v-col>
        <!-- 입퇴장 -->
        <v-col
          v-else-if="message.flag == 3"
          class="text-col-1 xs-font text-center my-3"
        >
          {{ message.content }}
        </v-col>
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
        disabled
      >
        <v-icon>mdi-arrow-up</v-icon>
      </v-btn>
    </v-form>
  </v-sheet>
</template>

<script>
import StompJS from "stompjs";
import SockJS from "sockjs-client";
import { mapState, mapActions } from "vuex";
import {
  getLastReadIdx,
  getNewChatMessage,
  saveLastMessage,
  deleteLastMessage,
} from "@/api/modules/websocket";
import { getPrevChat } from "@/api/modules/chat";
import { removeChat } from "@/api/modules/user";
import InfiniteLoading from "vue-infinite-loading";

export default {
  components: { InfiniteLoading },
  data() {
    return {
      serverURL: `${process.env.VUE_APP_API_SERVICE_URL}/chat-service`,
      messages: [],
      room: [],
      content: null,
      lastReadIdx: "", //마지막으로 읽은 채팅 인덱스
      socket: null,
      lastActiveTime: null,
      mySessionId: null,
      firstIdx: null, //이전 대화 불러올때 인덱스
      isScroll: false,
    };
  },
  watch: {
    messages() {
      //메시지 시간 수정
      for (let i = 0; i < this.messages.length; i++) {
        let mtime = this.messages[i].mtime;
        if (Array.isArray(mtime)) {
          //메시지가 배열 타입이라면 바꿔줌
          this.messages[i].mtime = `${mtime[3]}:${mtime[4]}`;
        }
      }
      //화면에 추가되었을 때 스크롤 처리
      this.$nextTick(() => {
        //스크롤 위치 수정
        const chatArea = document.querySelector(".chat-area");
        if (!this.isScroll) {
          chatArea.scrollTo({ top: chatArea.scrollHeight, behavior: "smooth" });
        }
      });
    },
    isDelete() {
      if (this.isDelete) {
        //채팅방 삭제하기 버튼을 눌렀을 경우
        //채팅방 삭제
        this.exitWebSocket();
        //isDelete -> false 변경
        // this.setIsDelete(false);
        //지금 선택한 채팅방 제거
        this.clearNowChatRoom();
      }
    },
  },
  computed: {
    ...mapState("chatStore", ["nowChatRoom", "isDelete"]),
    ...mapState("emojiStore", ["myPageView"]),
  },
  async created() {
    this.room = this.nowChatRoom;
    // console.log("이 방의 정보", this.room);
    // console.log("이 방의 rid >>> ", this.room.rid);
    // console.log("이 방의 id >>> ", this.room.id);

    this.lastActiveTime = Date.now();
    setInterval(() => {
      this.checkUserActivity();
    }, 300000);

    //마지막 메시지 조회
    this.lastReadIdx = await getLastReadIdx(this.room.id);
    // console.log("마지막 메시지 인덱스 axios 이후>>", this.lastReadIdx);
    if (this.lastReadIdx == null) {
      alert("마지막 메시지 조회 실패");
    } else if (this.lastReadIdx == 0) {
      this.connect();
    } else {
      // await this.getNewMessage(this.lastReadIdx);
      this.connect();
    }
  },
  mounted() {
    // 상위 스크롤 막기
    let myPageView = this.myPageView;
    // console.log("상위 스크롤 스타일 값", myPageView.style);
    myPageView.style.overflow = "hidden";
    // alert("마운트 됨 이벤트 등록");
    // window.addEventListener("beforeunload", this.disconnectWebSocket);
    // 스크롤 처리
    const chatArea = document.querySelector(".chat-area");
    chatArea.scrollTo(0, chatArea.scrollHeight);
  },
  beforeDestroy() {
    //상위 스크롤 되돌리기
    // 상위 스크롤 막기
    let myPageView = this.myPageView;
    // console.log("나가기 전 상위 스크롤 스타일 값", myPageView.style);
    myPageView.style.overflow = "scroll";
    this.disconnectWebSocket();
    // window.removeEventListener("beforeunload", this.disconnectWebSocket);
  },
  methods: {
    ...mapActions("chatStore", ["setIsDelete", "clearNowChatRoom"]),
    async infiniteHandler($state) {
      // console.log("0 핸들러요청", $state);
      if (this.lastReadIdx === "") {
        // console.log(" 1아직 마지막 인덱스 못불러옴");
        $state.reset();
        return;
      }
      // console.log(" 1있ㅇ요", this.lastReadIdx, this.firstIdx);

      if (this.lastReadIdx == 0) {
        // console.log("2 채팅방 처음 들어와서 불러올거 없음");
        $state.complete();
        return;
      }

      //읽지 않은 채팅 불러오기
      if (this.firstIdx == null) {
        // console.log("3 안 읽은 채팅 불러올게");
        await this.getNewMessage(this.lastReadIdx);
      }

      //처음 입장시 모든 요청 받아옴. 더이상 핸들러 처리 불필요
      if (this.firstIdx == 0) {
        // console.log("3 퍼스트인덱스 0이라서 핸들러 종료");
        $state.complete();
        return;
      }

      //위로 스크롤 했을 때 이전 메시지 불러오기
      const params = {
        rid: this.room.id,
        idx: this.firstIdx,
      };

      // console.log("3 퍼스트인덱스 0 아니라서 이전 메시지 더 불러올게");
      await getPrevChat(params, ({ data }) => {
        var result = data.data;
        // console.log("이전 채팅 데이터 result >> ", result);
        if (result.chatList.length > 0) {
          this.isScroll = true;
          this.messages.unshift(...result.chatList);
          this.firstIdx = result.firstIdx[0];
          $state.loaded();
          if (this.firstIdx == 0) {
            // console.log("4 이전 채팅 불러왔는데 퍼스트인덱스 0 이라서 끝낼게");
            $state.complete();
          }
        } else {
          console.log("이제 이전 채팅 없어");
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
      console.log("this socket>", this.socket);

      this.socket.connect(
        headers,
        (frame) => {
          console.log("소켓 연결 성공: ", frame);
          // console.log("아이디달라고 ", sockJs._transport.url);
          const url = sockJs._transport.url;
          const regex = /\/(\w+)\/websocket$/;
          const sessionId = url.match(regex)[1];
          // console.log("정규식햇덩~", sessionId); // n2dzsmev
          this.mySessionId = sessionId;

          this.socket.send(
            "/pub/chat/enter",
            { Authorization: token },
            JSON.stringify(this.room.id)
          );

          this.socket.subscribe(`/sub/chat/${this.room.id}`, (msg) => {
            //시간 수정
            let nowMsg = JSON.parse(msg.body);
            nowMsg.mtime = nowMsg.mtime.split("T")[1].substring(0, 5);
            this.messages.push(nowMsg);
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
      if (this.content == null) return;

      let token = "Bearer " + sessionStorage.getItem("access-token");
      this.isScroll = false;

      if (this.socket && this.socket.connected) {
        const msg = {
          rid: this.room.id,
          content: this.content,
        };
        // console.log("시간!!", new Date());
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
      //채팅방에 들어왔을 때,
      //채팅방에 위로 스크롤을 올렸을 때

      var result = await getNewChatMessage({ id: this.room.id, idx: idx });
      // console.log("읽지않은채팅 불러오기 >> ", result);
      //return chatList, firstIdx
      this.firstIdx = result.firstIdx[0];
      if (result.chatList.length == 0) return;
      this.messages.push(...result.chatList);
    },
    checkUserActivity() {
      const timeDiff = Date.now() - this.lastActiveTime;
      // 5분 동안 활동이 없으면 WebSocket disconnect
      if (timeDiff > 300000) {
        this.disconnectWebSocket();
      }
    },
    disconnectWebSocket() {
      // 마지막 메시지 저장 -> 그냥 나가기에만 실행, 삭제시는 실행 X
      if (!this.isDelete) {
        saveLastMessage(this.room.id);
      }

      // this.socket.disconnect(() => {
      //   console.log("WebSocket 연결 종료");
      //   this.$router.push("/my/chat");
      // });

      this.socket.disconnect(() => {});
      this.setIsDelete(false);
      console.log("WebSocket 연결 종료");
      // this.$router.push("/my/chat");
    },
    exitWebSocket() {
      // console.log("채팅방 삭제해줘용", this.room.id);
      let token = "Bearer " + sessionStorage.getItem("access-token");
      this.socket.send(
        "/pub/chat/exit",
        { Authorization: token },
        JSON.stringify(this.room.id)
      );
      deleteLastMessage(this.room.id);
      removeChat(
        this.room.id,
        ({ data }) => {
          console.log("채팅방 삭제 ", data);
          this.$router.push("/my/chat");
        },
        (error) => {
          console.log(error);
          alert("삭제 중 문제 발생");
          this.$router.push("/my/chat");
        }
      );
    },
  },
};
</script>

<style scoped>
.chat-room-container {
  /* height: 100vh;
  overflow: hidden; */
  /* height: 90vh; */
  height: 100%;
  overflow: hidden;

  display: flex;
  flex-direction: column;
}
.chat-area {
  height: 90%;
  overflow: scroll;
  /* height: 90%;
  overflow: scroll;
  margin-bottom: 5%; */
  /* overflow: scroll; */
  /* height: 90%;
  overflow: scroll;
  padding-top: 85px; */
}
.send-area {
  height: 10%;
  margin: 3%;
  /* position: absolute;
  width: 100%;
  padding: 3%;
  bottom: 0; */
  /* height: 10%; */
  /* margin: 3%; */

  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: var(--main-col-1);
}
</style>
