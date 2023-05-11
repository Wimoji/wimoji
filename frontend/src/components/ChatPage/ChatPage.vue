<template>
  <v-sheet color="var(--col-empty)" class="chat-list" v-if="rooms != null">
    <v-sheet
      @click="goRoom(room)"
      class="ma-3 d-flex justify-start px-5 py-4 rounded-xl align-center"
      v-for="room in rooms"
      :key="room.id"
    >
      <v-avatar class="mr-5">
        <v-img :src="emojiCategory[room.eid].link"></v-img>
      </v-avatar>
      <div class="xs-font main-font-bd">{{ room.title }}</div>
    </v-sheet>
  </v-sheet>
  <v-sheet v-else>
    <div class="xl-font text-center mt-10">참여한 채팅이 없어요... 😂</div>
  </v-sheet>
</template>

<script>
import { getUserChatRooms } from "@/api/modules/user";
import { mapState, mapActions } from "vuex";

export default {
  data() {
    return {
      rooms: [],
      name: "",
      emoji: "",
      limit: "",
    };
  },
  computed: {
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  created() {
    this.getMyRooms();
  },
  methods: {
    ...mapActions("chatStore", ["setNowChatRoom"]),
    async getMyRooms() {
      //사용자가 참여한 채팅 목록 불러오기
      await getUserChatRooms(
        ({ data }) => {
          if (data.success) {
            // console.log("data >> ", data);
            this.rooms = data.data;
            // console.log("this room >> ", this.rooms);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    goRoom(room) {
      //지금 채팅방 정보 설정
      // console.log("this room >> ", room);
      this.setNowChatRoom(room);
      this.$router.push({
        name: "chatting",
        params: { roomId: room.rid, data: room },
      });
    },
  },
};
</script>

<style>
.chat-list {
  height: 100vh - 130px;
  margin: 3%;
  overflow: scroll;
}
/* .chat-items {
  height: 100%;
} */
</style>
