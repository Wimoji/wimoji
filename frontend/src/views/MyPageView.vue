<template>
  <div class="my-page d-flex flex-column">
    <div class="my-headers">
      <my-header v-if="myHeaderPage.includes($route.name)"></my-header>
      <chat-room-header v-if="chatRoomPage.includes($route.name)">
      </chat-room-header>
    </div>
    <div ref="myPageView" class="my-page-view"><router-view></router-view></div>
  </div>
</template>

<script>
import ChatRoomHeader from "./Headers/ChatRoomHeader.vue";
import MyHeader from "./Headers/MyHeader.vue";
import { mapActions } from "vuex";
export default {
  components: { MyHeader, ChatRoomHeader },
  data() {
    return {
      myHeaderPage: ["my", "chat", "emoji"],
      chatRoomPage: ["chatting"],
    };
  },
  mounted() {
    this.$nextTick(() => {
      const myPageView = this.$refs.myPageView;
      console.log("지금 마이페이지 값", myPageView.style);
      this.setMyPageView(myPageView);
    });
  },
  methods: {
    ...mapActions("emojiStore", ["setMyPageView"]),
  },
};
</script>

<style>
.my-page {
  height: 100vh;
  /* height: 100%; */
}
.my-page-view {
  /* height: 100vh; */
  height: 100%;
  overflow: scroll;
}
</style>
