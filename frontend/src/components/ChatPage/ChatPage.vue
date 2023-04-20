<template>
  <div>채팅 목록
    <v-btn @click="goChatting">채팅방 들어가기</v-btn>
  </div>
</template>

<script>
export default {
  methods: {
    goChatting() {
      this.$router.push("/chat/1");
    },
  }
}
</script>

<style></style>