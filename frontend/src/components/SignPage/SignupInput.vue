<template>
  <v-sheet
    class="input-container d-flex flex-column col-transparent mx-auto mt-15"
    width="80%"
    height="100%"
  >
    <v-container class="input-area">
      <v-container
        class="input-title text-col-1 xl-font main-font-bd d-flex justify-center mb-5"
        >회원가입</v-container
      >
      <v-container class="input-form">
        <v-form ref="form">
          <input type="text" style="display: none" />
          <v-text-field
            class="sign"
            v-model="nickname"
            :rules="nicknameRules"
            rounded
            solo
            background-color="white"
          >
            <template v-slot:label>
              <span class="xs-font text-col-3">닉네임</span>
            </template>
            <template #prepend-inner>
              <v-icon :color="'var(--text-col-3)'" size="20" class="mr-4">
                mdi-rename-outline
              </v-icon>
            </template>
          </v-text-field>
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
            @keyup.enter="goSignup"
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
            height="50px"
            width="100%"
            rounded
            color="var(--main-col-5)"
            class="white-col-1"
            @click="goSignup"
            >회원가입</v-btn
          >
        </v-form>
      </v-container>
    </v-container>
  </v-sheet>
</template>

<script>
import { signup } from "@/api/modules/user";

export default {
  data() {
    return {
      id: "",
      password: "",
      nickname: "",
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
      nicknameRules: [(v) => !!v || "닉네임을 입력해주세요."],
    };
  },
  methods: {
    async goSignup() {
      //유효성 검증
      const validate = this.$refs.form.validate();
      const data = {
        uid: this.id,
        password: this.password,
        nickname: this.nickname,
      };
      // console.log("data >> ", data);
      if (validate) {
        await signup(
          data,
          ({ data }) => {
            // console.log("ㅎ회원가입>>", data);
            if (data.success) {
              alert(
                `회원가입이 완료되었습니다. 반갑습니다 ${this.nickname}님!`
              );
              this.$router.push("/login");
            } else {
              alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
            }
          },
          (error) => {
            // console.log("ㅎ회원가입에러>>", error);
            alert(error.response.data.message);
            this.id = "";
          }
        );
      }
    },
  },
};
</script>

<style scoped></style>
