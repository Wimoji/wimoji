<template>
  <div>
    <h2>채팅</h2>
    <div>
      <button @click="goChat">뒤로 가기</button>
      <img src="https://item.kakaocdn.net/do/1318d6316a603a75dc7021a51ddc6bc2617ea012db208c18f6e83b1a90a7baa7">
      <p>상대 닉네임</p>
      <p>이모지 제목</p>
    </div>
    <div>
      <div v-for="(message, index) in messages" :key="index">
        {{ message }}
      </div>
    </div>
    <div class="container">
      <div class="form">
        <input v-model="messageText" placeholder="입력해주세요" @keyup.enter="sendMessage">
        <button @click="sendMessage">Send</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      webSocket: null,
      messages: [],
      messageText: '',
    };
  },
  created() {
    this.connectWebSocket();
  },
  methods: {
    goChat() {
      this.$router.push("/chat");
    },
    connectWebSocket() {
      // const protocol = location.protocol === 'https:' ? 'wss:' : 'ws:';
      // const webSocketUrl = `${protocol}//${location.host}/chat`;
      const webSocketUrl = "ws://localhost:8080/ws/chat"
      this.webSocket = new WebSocket(webSocketUrl);
      this.webSocket.onopen = () => {
        console.log('WebSocket connection opened!');
      };
      this.webSocket.onmessage = (event) => {
        const message = event.data;
        this.messages.push(message);
      };
      this.webSocket.onclose = () => {
        console.log('WebSocket connection closed!');
        setTimeout(() => this.connectWebSocket(), 5000);
      };
    },
    sendMessage() {
      if (this.webSocket.readyState === WebSocket.OPEN) {
        this.webSocket.send(this.messageText);
        this.messageText = '';
      } else {
        console.log('WebSocket is not open');
        this.connectWebSocket();
      }
    },
  },
};
</script>

<style>
  img {
    width: 100px;
    height: 100px;  
  }
  .container {
    display: flex;
    flex-direction: column;
    /* height: 100vh; */
  }
  .content {
    flex: 1;
  }
  .form {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    height: 50px;
    background-color: #fff;
    padding: 10px;
    box-sizing: border-box;
  }
</style>