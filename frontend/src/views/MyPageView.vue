<template>
  <div class="my-page d-flex flex-column">
    <my-header v-if="myHeaderPage.includes($route.name)"></my-header>
    <chat-room-header v-if="chatRoomPage.includes($route.name)">
    </chat-room-header>
    <div class="my-page-view"><router-view></router-view></div>
  </div>
</template>

<script>
import ChatRoomHeader from "./Headers/ChatRoomHeader.vue";
import MyHeader from "./Headers/MyHeader.vue";
export default {
  components: { MyHeader, ChatRoomHeader },
  data() {
    return {
      myHeaderPage: ["my", "chat", "emoji"],
      chatRoomPage: ["chatting"],
    };
  },
};
</script>

<style>
.my-page {
  height: 100vh;
}
.my-page-view {
  height: 100%;
  overflow: scroll;
}
</style>
