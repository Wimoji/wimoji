<template>
  <div>
    <h3>채팅 목록</h3>
    <form>
      <label>이모지: 
        <input type="text" v-model="emoji">
      </label>
      <label>제목: 
        <input type="text" v-model="name">
      </label>
      <label>최대 인원: 
        <input type="text" v-model="limit">
      </label>
      <button @click="makeRoom">채팅방 만들기</button>
    </form>
    <ul>
      <li v-for="room in rooms" :key="room.id">
        <p>이모지: <img :src="room.emoji" alt="방 이모지"> 채팅방 이름 {{ room.name }}</p>
        <button @click="goRoom(room)">채팅방 들어가기</button>
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
      name: '',
      emoji: '',
      limit: '',
    }
  },
  mounted() {
    this.getRooms();
  },
  methods: {
    async getRooms() {
      try {
        const response = await axios.get(`${this.serverURL}`);
        this.rooms = response.data.data;
      } catch (error) {
        console.error(error);
      }
    },
    goRoom(room) {
      this.$router.push( { name: 'chatting', params: { roomId: room.rid, data: room } });
    },
    makeRoom(event) {
      event.preventDefault();

      const chatRoomReq = {
        name: this.name,
        emoji: this.emoji,
        participant: 1,
        limit: parseInt(this.limit),
        userList: [],
      };
      if (chatRoomReq.name.trim() === '' || chatRoomReq.emoji.trim() === '') {
        console.log('제목과 이모지는 필수 입력 사항입니다.');
        return;
      }

      axios.post(`${this.serverURL}`, chatRoomReq)
        .then(response => {
          console.log(response);
        })
        .catch(error => {
          console.log(error);
        });
    },
  }
}
</script>

<style></style>