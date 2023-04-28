<template>
  <div>
    <div class="info">
      <button @click="goChat">뒤로 가기</button>
      <p>이모지: <img :src="room.emoji" alt="방 이모지"></p>
      <p>방 이름: {{ room.name }}</p>
      <p>이름: {{ userName }}</p>
      <p>아이디: {{ userId }}</p>
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
      <button @click="disconnectWebSocket">채팅방 나가기</button>
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
      serverURL: 'http://70.12.246.229:8080',
      messages: [],
      room: [],
      lastReadId: '',
      userId: '',
      userName: '',
      content: '',
      socket: null,
      lastActiveTime: null,
    };
  },
  created() {
    this.room = this.$route.params.data;
    this.userId = sessionStorage.getItem("userId");
    this.userName = sessionStorage.getItem("nickname");
    console.log("이 방의 rid >>> ", this.room.rid);
    console.log("이 방의 id >>> ", this.room.id);
    console.log("이 방의 userId >>> ", this.userId);
    console.log("이 방의 userName >>> ", this.userName);

    this.lastActiveTime = Date.now();
    setInterval(this.checkUserActivity, 300000);
    
    this.connect();
    // this.getLastReadId().then((lastReadId) => {
    //   if(!lastReadId) {
    //     this.connect();
    //   } else {
    //     this.getNewMessage(lastReadId);
    //   }
    // });
    
  },
  methods: {
    goChat() {
      this.$router.push("/chat");
    },
    getLastReadId() {
      return new Promise((resolve) => {
        axios.get("/read")
        .then((response) => {
          this.lastReadId = response.data.mid;
          resolve(this.lastReadId);
        }).catch(error => {
          // db 오류
          console.log(error);
        });
      });
    },
    connect() {
      const sockJs = new SockJS(`${this.serverURL}/ws/chat`);
      this.socket = StompJS.over(sockJs);

      const headers = {
       'userId': this.userId,
       'roomId': this.room.id,
      };
      this.socket.connect(headers, frame => {
        console.log('소켓 연결 성공: ', frame);
        this.socket.subscribe(`/sub/chat/${this.room.id}`, msg => {
          // console.log("전달 메세지: ", msg);
          this.messages.push(JSON.parse(msg.body));
        }), (error) => {
          console.log(error);
        };

        const msg = { 
          rid: this.room.id,
          userId: this.userId,
          userName: this.userName,
        };
        this.socket.send("/pub/chat/enter", { token: "" }, JSON.stringify(msg));
      }, error => {
        console.log("소켓 연결 실패", error);
        // rid 오류
        // 인원 최대
        if(error.command === "ERROR"){
          alert("채팅방의 인원이 최대입니다.")
          this.goChat()
        }
      });
    },
    sendMessage() {
      console.log("Send message:" + this.content);
      if (this.socket && this.socket.connected) {
        const msg = { 
          rid: this.room.id,
          sender: this.userId,
          content: this.content,
        };
        this.socket.send("/pub/chat/message", { token: "" }, JSON.stringify(msg));
        // 형식 오류
        // db 저장 오류
      }
      this.content = "";
      this.lastActiveTime = Date.now();
    },
    getNewMessage(id) {
      axios.get(`/unread` + id)
      .then((response) => {
        const newMessages = response.data;
        this.messages.push(...newMessages);
      }).catch(error => {
          // id 오류
          // db 오류
          console.log(error);
      });
    },
    checkUserActivity() {
      const timeDiff = Date.now() - this.lastActiveTime;
      // 5분 동안 활동이 없으면 WebSocket disconnect
      if (timeDiff > 300000) {
        this.disconnectWebSocket();
      }
    },
    disconnectWebSocket() {
      const msg = { 
        rid: this.room.id,
        userId: this.userId,
        userName: this.userName,
      };
      this.socket.send("/pub/chat/exit", { token: "" }, JSON.stringify(msg));

      this.socket.disconnect(() => {
        console.log('WebSocket 연결 종료.');
        this.goChat();
      });
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