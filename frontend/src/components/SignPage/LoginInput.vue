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
          <input type="text" style="display: none" />
          <v-text-field
            class="sign"
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
            class="sign"
            v-model="password"
            :rules="passwordRules"
            rounded
            solo
            type="password"
            background-color="white"
            @keyup.enter="goLogin"
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
            v-if="isClick && isAllOk"
            height="55px"
            width="100%"
            rounded
            color="var(--main-col-3)"
            class="white-col-1"
            loading
            @click="goLogin"
          ></v-btn>
          <v-btn
            v-else
            height="50px"
            width="100%"
            rounded
            color="var(--main-col-3)"
            class="white-col-1"
            @click="goLogin"
          >
            로그인
          </v-btn>
        </v-form>
      </v-container>
    </v-container>
    <v-container
      class="another-area d-flex flex-column align-center justify-space-around text-col-1"
    >
      <!-- <div>다른 방법으로 로그인 하기</div>
      <div class="social-login">구글 깃허브 페이스북 트위터</div> -->
      <div>계정이 없으신가요?</div>
      <div
        class="main-font-bd main-col-3"
        style="cursor: pointer"
        @click="goSignupPage"
      >
        회원가입 하기
      </div>
    </v-container>
  </v-sheet>
</template>

<script>
import { login } from "@/api/modules/user";
import { mapActions } from "vuex";

const userStore = "userStore";

export default {
  data() {
    return {
      id: "",
      password: "",
      idRules: [(v) => !!v || "아이디를 입력해주세요."],
      passwordRules: [(v) => !!v || "비밀번호를 입력해주세요."],
      isClick: false, //로그인 버튼을 눌렀는지 확인
      isAllOk: false,
    };
  },
  computed: {},
  methods: {
    ...mapActions(userStore, ["setLogin"]),
    goSignupPage() {
      this.$router.push("/signup");
    },
    async goLogin() {
      this.isClick = true;
      //로그인 유효성 검증
      const validate = this.$refs.form.validate();
      const data = {
        uid: this.id,
        password: this.password,
      };

      if (validate) {
        this.isAllOk = true;

        let result = await login(data);
        // console.log("로그인화면에서 받은값 >> ", result);
        if (result != null) {
          alert("로그인 성공");
          //세션 스토리지에 토큰 저장
          sessionStorage.setItem("access-token", result.accessToken);
          sessionStorage.setItem("refresh-token", result.refreshToken);

          //임시로 아이디도 전송 >> 이모지 서비스 수정되면 고칠 예정
          const user = {
            nickname: result.nickname,
            id: this.id,
          };
          this.setLogin(user); //로그인 정보 설정
          this.$router.push("/");
        } else {
          alert("로그인 실패");
          this.$router.push("/");
        }
      }
    },
  },
};
</script>

<style></style>
