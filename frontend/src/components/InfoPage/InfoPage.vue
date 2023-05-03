<template>
  <v-container>
    서비스를 소개하는 페이지입니다.
    <router-link to="/login">지금 시작하기</router-link>
  </v-container>
</template>

<script>
export default {
  methods: {
    goLogin() {
      this.$router.push("/login");
    },
  },
};
</script>

<style></style>
