<template>
  <div>
    <div class="info">
      <button @click="goChat">뒤로 가기</button>
      <p>이모지: <img :src="room.emoji" alt="방 이모지"></p>
      <p>방 이름: {{ room.name }}</p>
    </div>
    <div class="msg">
      <div v-for="message in messages" :key="message.id">
        <div>닉네임: {{ message.sender }}</div>
        <div>내용: {{ message.content }}</div>
      </div>
    </div>
    <div class="form">
      <input v-model="content" placeholder="입력해주세요" @keyup.enter="sendMessage">
      <button @click="sendMessage">Send</button>
    </div>
    <div>
      <button @click="exitWebSocket">채팅방 완전 나가기</button>
    </div>
  </div>
</template>

<script>
import StompJS from "stompjs";
import SockJS from "sockjs-client";
import axios from 'axios';

export default {
  data() {
    return {
      serverURL: 'http://70.12.246.229:8083',
      messages: [],
      room: [],
      content: '',
      lastReadIdx: '',
      socket: null,
      lastActiveTime: null,
    };
  },
  created() {
    this.room = this.$route.params.data;
    console.log("이 방의 rid >>> ", this.room.rid);
    console.log("이 방의 id >>> ", this.room.id);

    this.lastActiveTime = Date.now();
    setInterval(() => {
      this.checkUserActivity();
    }, 300000);
    
    this.getLastReadIdx()
    .then((lastReadIdx) => {
      if(!lastReadIdx) {
        this.connect();
      } else {
        this.getNewMessage(lastReadIdx);
      }
    }).catch(error => {
      console.log(error);
    });
    
  },
  mounted() {
    window.addEventListener("beforeprint", this.disconnectWebSocket);
  },
  beforeUnmount() {
    window.removeEventListener("beforeunload", this.disconnectWebSocket);
  },
  methods: {
    goChat() {
      this.$router.push("/chat");
    },
    getLastReadIdx() {
      return new Promise((resolve) => {
        axios.get(`${this.serverURL}/last`)
        .then((response) => {
          this.lastReadIdx = response.data.data;
          resolve(this.lastReadIdx);
        }).catch(error => {
          console.log(error);
        });
      });
    },
    connect() { // 여기 봐야 함
      const sockJs = new SockJS(`${this.serverURL}/ws/chat`);
      this.socket = StompJS.over(sockJs);

      const headers = {
       'userId': this.userId,
       'roomId': this.room.id,
      };
      this.socket.connect(headers, frame => {
        console.log('소켓 연결 성공: ', frame);
        this.socket.subscribe(`/sub/chat/${this.room.id}`, msg => {
          this.messages.push(JSON.parse(msg.body));
          // 메시지 id 저장
          console.log("전달 메시지>>>", msg);
        }), (error) => {
          console.log(error);
        };
      }, error => {
        console.log("소켓 연결 실패", error);
        // rid 오류, uid 오류, 인원 최대
        if(error.command === "ERROR"){
          alert("채팅방에 접속할 수 없습니다.")
          this.goChat()
        }
      });
    },
    getNewMessage(idx) { // request 수정
      axios.get(`${this.serverURL}/unread` + idx)
      .then((response) => {
        const newMessages = response.data;
        this.messages.push(...newMessages);
      }).catch(error => {
          // id 오류, db 오류
          console.log(error);
      });
    },
    sendMessage() {
      if (this.socket && this.socket.connected) {
        const msg = { 
          rid: this.room.id,
          content: this.content,
        };
        this.socket.send("/pub/chat/message", { token: "" }, JSON.stringify(msg));
      }
      this.content = "";
      this.lastActiveTime = Date.now();
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
      const LastChatReq = { 
        rid: this.room.id,
        uid: this.userId,
        cid: null,
      };
       axios.post(`${this.serverURL}/last`, LastChatReq)
      .then(response => {
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });

      this.socket.disconnect(() => {
        console.log('WebSocket 연결 종료.');
        this.goChat();
      });
    },
    exitWebSocket() {
      this.socket.send("/pub/chat/exit", { token: "" }, { rid: this.room.id });
    },
  },
};
</script>

<style>
  img {
    width: 100px;
    height: 100px;
  }
</style>