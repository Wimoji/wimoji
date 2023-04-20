<template>
  <div>
    <div class="info">
      <button @click="goChat">뒤로 가기</button>
      <img src="https://item.kakaocdn.net/do/1318d6316a603a75dc7021a51ddc6bc2617ea012db208c18f6e83b1a90a7baa7">
      <p>이모지 제목</p>
    </div>
    <div class="msg">
      <div v-for="(message, index) in messages" :key="index">
        {{ message }}
      </div>
    </div>
    <div class="form">
      <input v-model="messageText" placeholder="입력해주세요" @keyup.enter="sendMessage">
      <button @click="sendMessage">Send</button>
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
      // const username = [];
      const protocol = location.protocol === 'https:' ? 'wss:' : 'ws:';
      // const webSocketUrl = `${protocol}//${location.host}/ws/chat`;
      const webSocketUrl = `${protocol}//70.12.246.229:8080/ws/chat`;
      console.log(location.host);

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
</style>