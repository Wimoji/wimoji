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
      token: '',
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
      if(lastReadIdx === 0) {
        this.connect();
      } else {
        this.getNewMessage(lastReadIdx);
      }
    }).catch(error => {
      console.log("마지막 메시지 조회 실패 ", error);
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
        const headers = { 'Authorization': `Bearer ${this.token}` };
        axios.get(`${this.serverURL}/last/${this.room.id}`, { headers })
        .then((response) => {
          console.log(response);
          this.lastReadIdx = response.data.data;
          resolve(this.lastReadIdx);
        }).catch(error => {
          console.log(error);
        });
      });
    },
    connect() {
      const sockJs = new SockJS(`${this.serverURL}/ws/chat`);
      this.socket = StompJS.over(sockJs);

      const headers = {
        'Authorization': `Bearer ${this.token}`,
        'rid': this.room.id,
      };
      this.socket.connect(headers, frame => {
        console.log('소켓 연결 성공: ', frame);
        this.socket.send("/pub/chat/enter", { Authorization: "" }, { rid: this.room.id });

        this.socket.subscribe(`/sub/chat/${this.room.id}`, msg => {
          this.messages.push(JSON.parse(msg.body));
        }), (error) => {
          console.log("메시지 수신 실패 ", error);
        };
      }, error => {
        console.log("소켓 연결 실패 ", error);
        // rid 오류, uid 오류, 인원 최대
        if(error.command === "ERROR"){
          alert("채팅방에 접속할 수 없습니다.")
          this.goChat()
        }
      });
    },
    sendMessage() {
      if (this.socket && this.socket.connected) {
        const msg = { 
          rid: this.room.id,
          content: this.content,
        };
        this.socket.send("/pub/chat/message", { Authorization: "" }, JSON.stringify(msg));
      }
      this.content = "";
      this.lastActiveTime = Date.now();
    },
    getNewMessage(idx) {
      axios.get(`${this.serverURL}/unread/${this.room.id}/${idx}`)
      .then((response) => {
        const newMessages = response.data.data;
        this.messages.push(...newMessages);
      }).catch(error => {
          console.log("새로운 메시지 출력 실패 ", error);
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
      // 마지막 메시지 저장
      const rid = this.room.id;
      axios.post(`${this.serverURL}/last`, rid)
      .then(response => {
        console.log("마지막 메시지>>>", response);
      })
      .catch(error => {
        console.log("메시지 저장 실패 ", error);
      });

      this.socket.disconnect(() => {
        console.log('WebSocket 연결 종료');
        this.goChat();
      });
    },
    exitWebSocket() {
      this.socket.send("/pub/chat/exit", { Authorization: "" }, { rid: this.room.id });
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