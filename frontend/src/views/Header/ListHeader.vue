<template>
  <div>
    <div>
      <v-btn @click="goBack">
        <v-icon>mdi-chevron-left</v-icon>
      </v-btn>
      목록
      <v-btn @click="deleteUser">
        <v-icon>mdi-account-remove-outline</v-icon>
      </v-btn>
    </div>
    <router-link to="chat">채팅</router-link> |
    <router-link to="myEmoji">나의 이모지</router-link>
  </div>
</template>

<script>
export default {
  methods: {
    goBack() {
      if (this.$router.history.length > 1) {
        this.$router.go(-1);
      } else {
        this.$router.push("/home");
      }
    },
    deleteUser() {
      alert("회원 탈퇴 버튼을 눌렀습니다.");
    },
  },
};
</script>

<style></style>
