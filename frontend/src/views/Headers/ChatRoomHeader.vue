<template>
  <div>
    <h1>채팅방 헤더입니다</h1>
    <v-btn @click="goBack">뒤로가기</v-btn>
  </div>
</template>

<script>
export default {
  methods: {
    goBack() {
      if (this.$router.history.length > 1) {
        this.$router.go(-1);
      } else {
        this.$router.push("/my/chat");
      }
    },
  },
};
</script>

<style></style>
