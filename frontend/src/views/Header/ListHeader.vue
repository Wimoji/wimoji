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
import { deleteUser } from "@/api/modules/user";
import { mapActions } from "vuex";

export default {
  methods: {
    ...mapActions("userStore", ["setIsLogin"]),
    goBack() {
      if (this.$router.history.length > 1) {
        this.$router.go(-1);
      } else {
        this.$router.push("/");
      }
    },
    async deleteUser() {
      if (confirm("회원 탈퇴 하시겠습니까?")) {
        await deleteUser(
          ({ data }) => {
            console.log(data);
            if (data.success) {
              alert("회원 탈퇴가 완료되었습니다.");
              //세션에서 토큰 제거
              sessionStorage.clear();
              //로그인 상태 변경
              this.setIsLogin(false);
              this.$router.push("/");
            }
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
  },
};
</script>

<style></style>
