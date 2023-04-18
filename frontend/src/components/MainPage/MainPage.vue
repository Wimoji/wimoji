<template>
  <div class="pa-10">
    서비스를 소개하는 페이지입니다.
    <v-btn @click="goLogin">지금 시작하기</v-btn>
  </div>
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
