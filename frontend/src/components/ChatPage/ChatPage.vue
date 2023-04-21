<template>
  <div>
    <h3>채팅 목록</h3>
    <ul>
      <li v-for="room in rooms" :key="room.id">
        이름 {{ room.name }}
        <button @click="goRoom(room.id)">채팅방 들어가기</button>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      serverURL: 'http://70.12.246.229:8080',
      rooms: [],
    }
  },
  mounted() {
    this.getRooms();
  },
  methods: {
    async getRooms() {
      try {
        const response = await axios.get(`${this.serverURL}/chatroom`);
        this.rooms = response.data.data;
        console.log(response);
      } catch (error) {
        console.error(error);
      }
    },
    goRoom(roomId) {
      this.$router.push(`/chat/${roomId}`);
    },
  }
}
</script>

<style></style>