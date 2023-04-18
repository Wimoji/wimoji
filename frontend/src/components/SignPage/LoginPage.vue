<template>
  <div>
    <div>로그인 페이지입니다</div>
    <div>
      <v-btn @click="goLogin">로그인하기</v-btn>
    </div>
    <div>다른 방법으로 로그인 하기</div>
    <div>
      계정이 없으신가요?
      <span style="color: var(--main-col-3); cursor: pointer" @click="goSignup"
        >회원가입 하기</span
      >
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goSignup() {
      this.$router.push("/signup");
    },
    goLogin() {
      this.$router.push("/home");
    },
  },
};
</script>

<style></style>
