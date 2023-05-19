<template>
  <div>
    <div>회원가입 페이지입니다.</div>
    <div>
      <v-btn @click="goSignUp">회원가입하기</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goSignUp() {
      this.$router.push("/login");
    },
  },
};
</script>

<style></style>
