<template>
  <v-sheet class="mt-16" height="60vh" color="var(--col-empty)">
    <!-- 로그인 영역 -->
    <v-row>
      <v-col align="center">
        <v-container class="mb-8">
          <div class="text-col-1 xl-font main-font-bd">로그인</div>
        </v-container>
        <v-sheet width="80%" color="var(--col-empty)">
          <v-form ref="form">
            <input type="text" style="display: none" />
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
              rounded
              width="100%"
              height="50px"
              color="var(--main-col-3)"
              class="white-col-1"
              loading
              @click="goLogin"
            ></v-btn>
            <v-btn
              v-else
              rounded
              width="100%"
              height="50px"
              color="var(--main-col-3)"
              class="white-col-1"
              @click="goLogin"
            >
              로그인
            </v-btn>
          </v-form>
        </v-sheet>
      </v-col>
    </v-row>
    <!-- 회원가입 이동 영역 -->
    <v-row>
      <v-col class="go-signup-area" align="center">
        <div class="sm-font text-col-1 mb-4">계정이 없으신가요?</div>
        <span
          style="cursor: pointer"
          @click="goSignupPage"
          class="main-font-bd main-col-3 sm-font"
        >
          회원가입 하러가기
        </span>
      </v-col>
    </v-row>
  </v-sheet>
</template>

<script>
import { login } from "@/api/modules/user";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      id: "",
      password: "",
      idRules: [(v) => !!v || "아이디를 입력해주세요."],
      passwordRules: [(v) => !!v || "비밀번호를 입력해주세요."],
      isClick: false, //버튼을 눌렀는지 확인
      isAllOk: false,
    };
  },
  computed: {},
  methods: {
    ...mapActions("userStore", ["setLogin"]),
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

          //닉네임 저장
          const user = {
            nickname: result.nickname,
          };

          this.setLogin(user); //로그인 정보 설정
          this.$router.replace("/");
        } else {
          alert("등록된 사용자 정보가 없습니다.");
          this.isClick = false;
          this.isAllOk = false;
          this.id = null;
          this.password = null;
          this.$router.replace("/login");
        }
      }
    },
  },
};
</script>

<style>
.go-signup-area {
  margin-top: 20%;
}
</style>
