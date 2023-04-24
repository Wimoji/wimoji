<template>
  <div>
    <div class="info">
      <button @click="goChat">뒤로 가기</button>
      <img src="https://item.kakaocdn.net/do/1318d6316a603a75dc7021a51ddc6bc2617ea012db208c18f6e83b1a90a7baa7">
      <p>이모지: {{ room.emoji }}</p>
      <p>방 이름: {{ room.name }}</p>
    </div>
    <div class="msg">
      <div v-for="(message, index) in messages" :key="index">
        <div>닉네임: {{ message.sender }}</div>
        <div>내용: {{ message.content }}</div>
      </div>
    </div>
    <div class="form">
      <input v-model="sender" placeholder="닉네임란">
      <input v-model="content" placeholder="입력해주세요" @keyup.enter="sendMessage">
      <button @click="sendMessage">Send</button>
    </div>
  </div>
</template>

<script>
import StompJS from "stompjs";
import SockJS from "sockjs-client";

export default {
  data() {
    return {
      serverURL: 'http://70.12.246.229:8080',
      messages: [],
      rid: '',
      room: [],
      sender: '',
      content: '',
      socket: null,
    };
  },
  created() {
    this.connect();
    this.room = this.$route.params.data;
  },
  mounted() {
    this.getRoom();
  },
  methods: {
    goChat() {
      this.$router.push("/chat");
    },
    getRoom() {
      const id = this.$route.params.roomId;
      this.rid = id;
      console.log("이 방의 id >>> ", this.rid);
    },
    sendMessage() {
      console.log("Send message:" + this.content);
      if (this.socket && this.socket.connected) {
        const msg = { 
          rid: this.rid,
          sendTime: "23042400",
          sender: this.sender,
          content: this.content,
        };
        this.socket.send("/pub/chat/message", { token: "" }, JSON.stringify(msg));
      }
      this.content = "";
    },
    connect() {
      var sock = new SockJS(`${this.serverURL}/ws/chat`);
      this.socket = StompJS.over(sock);
      console.log('소켓 연결을 시도합니다. 서버 주소: ', this.serverURL);

      this.socket.connect(
      {},
      frame => {
        console.log('소켓 연결 성공: ', frame);
        this.socket.subscribe(`/sub/chat/${this.rid}`,
          msg => {
            console.log("전달 메세지: ", msg);
            this.messages.push(JSON.parse(msg.body));
          }
        );
      },
      error => {
        console.log("소켓 연결 실패", error);
      }
    );
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