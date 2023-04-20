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
            type="password"
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
      class="another-area d-flex flex-column align-center justify-space-around text-col-1"
    >
      <div>다른 방법으로 로그인 하기</div>
      <div class="social-login">구글 깃허브 페이스북 트위터</div>
      <div>
        계정이 없으신가요?
        <span
          class="main-font-bd main-col-3"
          style="cursor: pointer"
          @click="goSignupPage"
          >회원가입 하기</span
        >
      </div>
    </v-container>
  </v-sheet>
</template>

<script>
import { login } from "@/api/modules/user";
import { mapActions, mapState } from "vuex";

const userStore = "userStore";

export default {
  data() {
    return {
      id: "",
      password: "",
      idRules: [(v) => !!v || "아이디를 입력해주세요."],
      passwordRules: [(v) => !!v || "비밀번호를 입력해주세요."],
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin"]),
  },
  methods: {
    ...mapActions(userStore, ["setIsLogin"]),
    goSignupPage() {
      this.$router.push("/signup");
    },
    async goLogin() {
      //로그인 유효성 검증
      const validate = this.$refs.form.validate();
      const data = {
        uid: this.id,
        password: this.password,
      };

      if (validate) {
        await login(
          data,
          ({ data }) => {
            // console.log(data);
            if (data.success) {
              alert("로그인 성공");
              //세션 스토리지에 토큰 저장
              sessionStorage.setItem("access-token", data.data.accessToken);
              sessionStorage.setItem("refresh-token", data.data.refreshToken);
              this.setIsLogin(true); //로그인 상태로 변경
              this.$router.push("/home");
            }
          },
          (error) => {
            console.log(error.response.data);
            if (error.response.data.code == 10000) {
              alert(error.response.data.message.split(" - ")[1]);
              //input 지워주기
              this.id = "";
              this.password = "";
            }
          }
        );
      }
    },
  },
};
</script>

<style></style>
