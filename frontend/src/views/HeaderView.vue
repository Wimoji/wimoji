<template>
  <div class="header-area">
    <home-header v-if="homePage.includes($route.name) && isLogin"></home-header>
    <my-header v-if="myHeaderPage.includes($route.name)"></my-header>
    <chat-room-header
      v-if="chatRoomPage.includes($route.name)"
    ></chat-room-header>
  </div>
</template>

<script>
import ChatRoomHeader from "@/views/Headers/ChatRoomHeader.vue";
import HomeHeader from "@/views/Headers/HomeHeader.vue";
import MyHeader from "@/views/Headers/MyHeader.vue";
import { mapState } from "vuex";

export default {
  data() {
    return {
      myHeaderPage: ["my", "chat", "emoji"],
      chatRoomPage: ["chatting"],
      homePage: ["main"],
    };
  },
  components: { HomeHeader, MyHeader, ChatRoomHeader },
  computed: {
    ...mapState("userStore", ["isLogin"]),
  },
};
</script>

<style>
.header-area {
  position: fixed;
  width: 100%;
  z-index: 2;
  max-width: 500px;
}
</style>
