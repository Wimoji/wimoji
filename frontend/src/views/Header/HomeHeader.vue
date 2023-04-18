<template>
  <div align="right">
    <!-- 홈 페이지의 헤더입니다. 이모지 생성, 채팅, 로그아웃 설정 -->
    <v-btn icon class="ma-3" @click="isClicked = !isClicked">
      <v-icon v-if="!isClicked">mdi-menu</v-icon>
      <v-icon v-else>mdi-close</v-icon>
    </v-btn>
    <v-card v-if="isClicked">
      <v-icon @click="goList">mdi-chat-outline</v-icon>
      <v-icon @click="goLogout">mdi-logout</v-icon>
    </v-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isClicked: false,
    };
  },
  methods: {
    goList() {
      this.$router.push("/chat");
    },
    goLogout() {
      location.href = `/`;
    },
  },
};
</script>

<style></style>
