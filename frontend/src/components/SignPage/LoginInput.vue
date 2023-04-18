<template>
  <v-sheet
    class="input-container d-flex flex-column justify-space-around col-transparent mx-auto"
    width="80%"
    height="100%"
  >
    <v-container class="input-area">
      <v-container
        class="input-title text-col-1 xl-font main-font-bd d-flex justify-center mb-5"
        >로그인</v-container
      >
      <v-container class="input-form">
        <v-form ref="form">
          <v-text-field
            v-model="id"
            :rules="idRules"
            rounded
            solo
            background-color="white"
          >
            <template v-slot:label>
              <span class="xs-font text-col-3">아이디</span>
            </template>
            <template #prepend-inner>
              <v-icon :color="'var(--text-col-3)'" size="20" class="mr-4">
                mdi-account-outline
              </v-icon>
            </template>
          </v-text-field>
          <v-text-field
            v-model="password"
            :rules="passwordRules"
            rounded
            solo
            background-color="white"
          >
            <template v-slot:label>
              <span class="xs-font text-col-3">비밀번호</span>
            </template>
            <template #prepend-inner>
              <v-icon :color="'var(--text-col-3)'" size="20" class="mr-4">
                mdi-lock-outline
              </v-icon>
            </template>
          </v-text-field>
          <v-btn
            height="55px"
            width="100%"
            rounded
            color="var(--main-col-3)"
            class="white-col-1"
            @click="goLogin"
            >로그인</v-btn
          >
        </v-form>
      </v-container>
    </v-container>
    <v-container
      class="another-area d-flex flex-column align-center justify-space-around"
    >
      <div>다른 방법으로 로그인 하기</div>
      <div class="social-login">구글 깃허브 페이스북 트위터</div>
      <div>
        계정이 없으신가요?
        <span class="main-font-bd main-col-3" @click="goSignup"
          >회원가입 하기</span
        >
      </div>
    </v-container>
  </v-sheet>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      password: "",
      idRules: [
        (v) => !!v || "아이디를 입력해주세요.",
        (v) =>
          /^[a-zA-Z0-9]+$/.test(v) || "아이디는 알파벳과 숫자만 가능합니다.",
      ],
      passwordRules: [
        (v) => !!v || "비밀번호를 입력해주세요.",
        (v) =>
          /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/.test(v) ||
          "비밀번호는 8~16자, 영문과 숫자를 조합해주세요.",
      ],
    };
  },
  methods: {
    goSignup() {
      this.$router.push("/signup");
    },
    goLogin() {
      //로그인 유효성 검증
      const validate = this.$refs.form.validate();

      if (validate) {
        alert("유효성 확인 완료");
      }
      // this.$router.push("/home");
    },
  },
};
</script>

<style></style>
